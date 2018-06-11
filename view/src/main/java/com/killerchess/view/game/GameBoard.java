package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
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

import java.util.HashSet;
import java.util.Set;

public class GameBoard extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final int UNPROPER_COORDINATE_VALUE = 100;

    private Tile[][] chessboard = new Tile[WIDTH][HEIGHT];

    private ChessmanImage currentChessmanImage;
    private int currentChessmanXCoordinate = UNPROPER_COORDINATE_VALUE;
    private int getCurrentChessmanYCoordinate = UNPROPER_COORDINATE_VALUE;

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private Button availableMovesButton;
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
        setAvailableMovesButton();
        root.getChildren().add(availableMovesButton);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = drawAndReturnTile(x, y);
                drawChessman(x, y, tile);
            }
        }

        return root;
    }

    private void setAvailableMovesButton() {
        availableMovesButton = new Button();
        availableMovesButton.setText("RUCHY");
        availableMovesButton.setLayoutX(800.0);
        availableMovesButton.setLayoutY(0.0);
        availableMovesButton.setPrefSize(100.0, 100.0);
        setAvailableMovesButtonOnClickFunction();
    }

    private void setAvailableMovesButtonOnClickFunction() {
        availableMovesButton.setOnMouseClicked(e -> {
            if (currentChessmanImage != null) {
                double currentChessmanX = currentChessmanImage.getPrevMouseX();
                double currentChessmanY = currentChessmanImage.getPrevMouseY();

                var possibleCaptures = currentChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                        new Pair<>(toBoard(currentChessmanY), toBoard(currentChessmanX)));

                Set<Pair<Integer, Integer>> possibleChessmans = null;
                Set<Pair<Integer, Integer>> possibleMoves = null;

                Set<Pair<Integer, Integer>> fieldsToHighLight = null;

                if (possibleCaptures == null || possibleCaptures.size() == 0) {
                    var chessmansWithGivenColor = chessBoard.getAllChessmansWithGivenColor(currentChessmanImage.getChessman().getColour());
                    if (chessmansWithGivenColor != null && chessmansWithGivenColor.size() != 0) {
                        for (Chessman possibleChessman : chessmansWithGivenColor) {
                            Pair<Integer, Integer> chessmanPosition = chessBoard.getChessmanPosition(possibleChessman);
                            if (chessmanPosition != null) {
                                chessboard[chessmanPosition.getValue()][chessmanPosition.getKey()].highlightRed();
                                var captures = possibleChessman.getPossibleCaptures(chessBoard, chessmanPosition);
                                if (captures != null && captures.size() != 0) {
                                    if (possibleChessmans == null)
                                        possibleChessmans = new HashSet<Pair<Integer, Integer>>();
                                    possibleChessmans.add(chessmanPosition);
                                }
                            }
                        }
                    }
                    if (possibleChessmans == null || possibleChessmans.size() == 0) {
                        possibleMoves = currentChessmanImage.getChessman().getPossibleMoves(chessBoard,
                                new Pair<>(toBoard(currentChessmanY), toBoard(currentChessmanX)));
                        if (possibleMoves != null || possibleMoves.size() != 0)
                            fieldsToHighLight = possibleMoves;
                    } else {
                        fieldsToHighLight = possibleChessmans;
                    }

                } else {
                    fieldsToHighLight = possibleCaptures;
                }

                if (fieldsToHighLight != null) {
                    for (int y = 0; y < HEIGHT; y++) {
                        for (int x = 0; x < WIDTH; x++) {
                            if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                                chessboard[y][x].highlightBlue();
                            }
                        }
                    }
                }
            }
        });
    }

    private Tile drawAndReturnTile(int x, int y) {
        Tile tile = new Tile((x + y) % 2 == 0, x, y);
        chessboard[x][y] = tile;
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
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY) {
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

    private void unhighlightAllBoard() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                chessboard[x][y].removeHighlight();
            }
        }
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage) {
        chessmanImage.setOnMousePressed(e -> {
            if (canPlayerMoveChessman(chessmanImage)) {
                unhighlightAllBoard();
                chessmanImage.setMouseX(e.getSceneX());
                chessmanImage.setMouseY(e.getSceneY());
                chessmanImage.setPrevMouseX((int) (e.getSceneX() / 100) * 100 + 7);
                chessmanImage.setPrevMouseY((int) (e.getSceneY() / 100) * 100 + 7);

                currentChessmanXCoordinate = toBoard(chessmanImage.getLayoutX());
                getCurrentChessmanYCoordinate = toBoard(chessmanImage.getLayoutY());
                currentChessmanImage = chessboard[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].getChessmanImage();
                chessboard[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].highlightGreen();
                System.out.println(currentChessmanXCoordinate);
                System.out.println(getCurrentChessmanYCoordinate);
            }
        });
        chessmanImage.setOnMouseDragged(e -> {
            if (canPlayerMoveChessman(chessmanImage)) {
                unhighlightAllBoard();
                chessmanImage.relocate(e.getSceneX(), e.getScreenY());
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
                        System.out.println(newX);
                        System.out.println(newY);
                        chessboard[newX][newY].setChessmanImage(chessmanImage);
                        break;
                    case KILL:
                        //   chessmanImage
                }
            }
        });
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
