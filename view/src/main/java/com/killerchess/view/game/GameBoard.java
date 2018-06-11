package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.EmptyField;
import com.killerchess.core.game.Game;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.core.user.User;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameBoard extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final int UNPROPER_COORDINATE_VALUE = 100;

    private Tile[][] chessBoardOfChessmansImages = new Tile[WIDTH][HEIGHT];

    private ChessmanImage currentChessmanImage;
    private int currentChessmanXCoordinate = UNPROPER_COORDINATE_VALUE;
    private int getCurrentChessmanYCoordinate = UNPROPER_COORDINATE_VALUE;

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private Button helpButton;
    private ChessBoard chessBoard;

    private StateInterpreter stateInterpreter;
    private Game game;
    LocalSessionSingleton localSessionSingleton;

    private Parent createContent(String gameBoardStateString) {
        this.stateInterpreter = new StateInterpreter();
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);
        this.localSessionSingleton = LocalSessionSingleton.getInstance();
        this.game = new Game();

        User host = new User("host");

        User guest = new User("guest");

        game.setHost(host);
        game.setGuest(guest);

        Pane root = new Pane();
        root.setPrefSize((WIDTH + 1) * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        setHelpButton();
        root.getChildren().add(helpButton);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = drawAndReturnTile(x, y);
                drawChessman(x, y, tile);
            }
        }

        return root;
    }

    private void setHelpButton() {
        helpButton = new Button();
        helpButton.setText("POMOC");
        helpButton.setLayoutX(800.0);
        helpButton.setLayoutY(0.0);
        helpButton.setPrefSize(100.0, 100.0);
        setHelpButtonMouseOnClickFunction();
    }

    private void setHelpButtonMouseOnClickFunction() {
        helpButton.setOnMouseClicked(e -> {
            if (currentChessmanImage != null) {
                double currentChessmanX = currentChessmanImage.getPrevMouseX();
                double currentChessmanY = currentChessmanImage.getPrevMouseY();

                var currentChessmanPossibleCaptures = currentChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                        new Pair<>(toBoard(currentChessmanY), toBoard(currentChessmanX)));

                if (currentChessmanPossibleCaptures == null)
                    currentChessmanPossibleCaptures = new HashSet<>();

                Set<Pair<Integer, Integer>> otherChessmansThatCanCapture = new HashSet<>();
                Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
                Set<Pair<Integer, Integer>> fieldsToHighLight = new HashSet<>();

                if (currentChessmanPossibleCaptures.isEmpty()) {
                    var chessmansWithGivenColor = chessBoard.getAllChessmansWithGivenColor(currentChessmanImage.getChessman().getColour());
                    if (!chessmansWithGivenColor.isEmpty()) {
                        findOtherChessmansThatCanCapture(chessmansWithGivenColor, otherChessmansThatCanCapture);
                    }
                    fieldsToHighLight = decideIfHighlightOtherChessmansOrPossibleMoves(otherChessmansThatCanCapture, currentChessmanX,
                                                                    currentChessmanY, fieldsToHighLight, possibleMoves);
                } else {
                    fieldsToHighLight = currentChessmanPossibleCaptures;
                }

                if (!fieldsToHighLight.isEmpty()) {
                    for (int y = 0; y < HEIGHT; y++)
                        for (int x = 0; x < WIDTH; x++) {
                            if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                                chessBoardOfChessmansImages[y][x].highlightBlue();
                            }
                        }
                }
            }
        });
    }

    private void findOtherChessmansThatCanCapture(Set<Chessman> chessmansWithGivenColor, Set<Pair<Integer, Integer>> possibleChessmans){
        for (Chessman possibleChessman : chessmansWithGivenColor) {
            Pair<Integer, Integer> chessmanPosition = chessBoard.getChessmanPosition(possibleChessman);
            if (chessmanPosition != null) {
                var captures = possibleChessman.getPossibleCaptures(chessBoard, chessmanPosition);
                if (captures != null && captures.size() != 0) {
                    if (possibleChessmans == null)
                        possibleChessmans = new HashSet<>();
                    possibleChessmans.add(chessmanPosition);
                }
            }
        }
    }

    private Set<Pair<Integer, Integer>> decideIfHighlightOtherChessmansOrPossibleMoves(Set<Pair<Integer, Integer>> otherChessmansThatCanCapture,
                                                                                       double currentChessmanX, double currentChessmanY,
                                                                                       Set<Pair<Integer, Integer>> fieldsToHighLight,
                                                                                       Set<Pair<Integer, Integer>> possibleMoves){
        if (otherChessmansThatCanCapture == null || otherChessmansThatCanCapture.size() == 0) {
            //WholeBoard();
            possibleMoves = currentChessmanImage.getChessman().getPossibleMoves(chessBoard,
                    new Pair<>(toBoard(currentChessmanY), toBoard(currentChessmanX)));
            if (possibleMoves != null && possibleMoves.size() != 0)
                fieldsToHighLight = possibleMoves;
        } else {
            fieldsToHighLight = otherChessmansThatCanCapture;
        }
        return fieldsToHighLight;
    }

    private Tile drawAndReturnTile(int x, int y) {
        Tile tile = new Tile((x + y) % 2 == 0, x, y);
        chessBoardOfChessmansImages[x][y] = tile;
        tileGroup.getChildren().add(tile);
        return tile;
    }

    private void drawChessman(int x, int y, Tile tile) {
        var chessman = chessBoard.getChessmanAt(y, x);
        ChessmanImage chessmanImage = createChessmanImageFromChessman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChessmanImageFromChessman(Chessman chessman, int x, int y) {
        ChessmanImage chessmanImage = createChessmanImage(chessman, 1, x, y);

        setChessmanImageMouseFunctions(chessmanImage);
        return chessmanImage;
    }

    private ChessmanImage createChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y) {
        return new ChessmanImage(chessman, chessmanStyleNumber, x, y);
    }

    private int toBoard(double pixel) {
        return (int) (pixel / TILE_SIZE);
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY) {
        if(chessmanImage != null) {
            double prevChessmanX = chessmanImage.getPrevMouseX();
            double prevChessmanY = chessmanImage.getPrevMouseY();
            if (chessmanImage.getChessman().getPossibleCaptures(chessBoard,
                    new Pair<>(toBoard(prevChessmanY), toBoard(prevChessmanX))).contains(new Pair<>(newY, newX))) {
                return new MoveResult(MoveType.KILL);
            } else if (chessmanImage.getChessman().getPossibleMoves(chessBoard,
                    new Pair<>(toBoard(prevChessmanY), toBoard(prevChessmanX))).contains(new Pair<>(newY, newX))) {

                return new MoveResult(MoveType.NORMAL);
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private void unhighlightAllBoard() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                chessBoardOfChessmansImages[x][y].removeHighlight();
            }
        }
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage) {
        if (chessmanImage != null) {
            chessmanImage.setOnMousePressed(e -> {
                if (canPlayerMoveChessman(chessmanImage)) {
                    unhighlightAllBoard();
                    chessmanImage.setMouseX(e.getSceneX());
                    chessmanImage.setMouseY(e.getSceneY());
                    chessmanImage.setPrevMouseX((int) (e.getSceneX() / 100) * 100 + 7);
                    chessmanImage.setPrevMouseY((int) (e.getSceneY() / 100) * 100 + 7);
                    chessmanImage.setPrevChessmanY(toBoard(e.getSceneY()));
                    chessmanImage.setPrevChessmanX(toBoard(e.getSceneX()));

                    currentChessmanXCoordinate = toBoard(chessmanImage.getLayoutX());
                    getCurrentChessmanYCoordinate = toBoard(chessmanImage.getLayoutY());
                    currentChessmanImage = chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].getChessmanImage();
                    chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].highlightGreen();
                }
            });
            chessmanImage.setOnMouseDragged(e -> {
                if (canPlayerMoveChessman(chessmanImage)) {
                    unhighlightAllBoard();
                    chessmanImage.relocate(e.getSceneX() - 50, e.getScreenY() - 50);
                }
            });

            chessmanImage.setOnMouseReleased(e -> {
                if (canPlayerMoveChessman(chessmanImage)) {
                    int newX = toBoard(chessmanImage.getLayoutX());
                    int newY = toBoard(chessmanImage.getLayoutY());

                    MoveResult result = tryMove(chessmanImage, newX, newY);

                    switch (result.getMoveType()) {
                        case NONE:
                            chessmanImage.abortMove();
                            break;
                        case NORMAL:
                            chessmanImage.move(newX, newY);
                            chessBoardOfChessmansImages[newX][newY].setChessmanImage(new ChessmanImage(chessmanImage));
                            chessBoardOfChessmansImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()]
                                    .getChessmanImage().setChessman(new EmptyField(chessmanImage.getChessman().getColour()));
                            updateChessBoardOfChessmans();
                            break;
                        case KILL:
                            //   chessmanImage
                    }
                }
            });
        }
    }



    private void printWholeBoard(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(chessBoard.getChessmanAt(y,x).getSymbol());
            }
            System.out.println("");
        }

    }

    private void updateChessBoardOfChessmans(){
       ArrayList<ArrayList<Chessman>> chessboardCurrentState = new ArrayList<>();
        for (int y = 0; y < HEIGHT; y++) {
            ArrayList<Chessman> currentChessmansRow = new ArrayList<>();
            for (int x = 0; x < WIDTH; x++) {
                currentChessmansRow.add(chessBoardOfChessmansImages[x][y].getChessmanImage().getChessman());
            }
            chessboardCurrentState.add(currentChessmansRow);
        }

        chessBoard = new ChessBoard(chessboardCurrentState);

        printWholeBoard();

       /* for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(chessBoard.getChessmanAt(y,x).getSymbol());
            }
            System.out.println("");
        }*/
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage) {
        // String userLogin = localSessionSingleton.getParameter("username");
        String userLogin = "guest";
        //guest always plays with black
        if (chessmanImage.getColour().getSymbol() == 'B' && game.getGuest().getLogin() == userLogin)
            return true;

            //host always plays with white
        else if (chessmanImage.getColour().getSymbol() == 'W' && game.getHost().getLogin() == userLogin)
            return true;
        return false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent("{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"" +
                "],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"]," +
                "\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":" +
                "[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\"," +
                "\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"" +
                ",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"" +
                "8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
