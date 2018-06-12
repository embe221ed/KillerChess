package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.EmptyField;
import javafx.scene.control.Button;
import com.killerchess.core.game.Game;
import com.killerchess.core.session.LocalSessionSingleton;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameBoard extends Application {

    static final int TILE_SIZE = 100;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private final int UNPROPER_COORDINATE_VALUE = 100;

    private Tile[][] chessBoardOfChessmansImages = new Tile[WIDTH][HEIGHT];

    private ChessmanImage currentChessmanImage;
    private int currentChessmanXCoordinate = UNPROPER_COORDINATE_VALUE;
    private int getCurrentChessmanYCoordinate = UNPROPER_COORDINATE_VALUE;

    private Stage stage;

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private Button helpButton;
    private ChessBoard chessBoard;
    private Pane root;

    private StateInterpreter stateInterpreter = new StateInterpreter();
    private Game game;
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();

   /* private Runnable listener = () -> {
        ResponseEntity<Boolean> responseEntity;
        UriComponentsBuilder builder;
        try {
            do {
                // czas pomiędzy kolejnymi zapytaniami
                Thread.sleep(5000);
                builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/gameStateChanged")
                        .queryParam("gameStateNumber", localSessionSingleton.
                                getParameter("gameStateNumber"));
                responseEntity = localSessionSingleton.
                        exchange(builder.toUriString(), HttpMethod.GET, null, Boolean.class);

            } while (!responseEntity.getBody());
            // zamiast tego będzie wywołanie metody z GameBoard.java, która aktualizuje GameState
            // pobierając tą informację z serwera
            // GameBoard.getInstance().updateGameState();
            System.out.println("You can move now");
            updateGameBoard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
*/
    private Parent createContent(String gameBoardStateString){
        this.stateInterpreter = new StateInterpreter();
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);
        this.localSessionSingleton = LocalSessionSingleton.getInstance();
        this.game = new Game();

        Pane root = new Pane();
        root.setPrefSize((WIDTH + 1) * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        setHelpButton();
        root.getChildren().add(helpButton);
        drawTiles();
        return root;
    }

    private void drawTiles() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = getDrawnTile(x, y);
                drawChessman(x, y, tile);
            }
        }
    }

    private void updateGameBoard() {
        ResponseEntity<String> responseEntity = localSessionSingleton
                .exchange("http://localhost:8080/gameBoard", HttpMethod.GET, null, String.class);
        chessBoard = stateInterpreter.convertJsonBoardToChessBoard(responseEntity.getBody());
        root = new Pane();
        tileGroup = new Group();
        chessmanGroup = new Group();
        root.getChildren().addAll(tileGroup, chessmanGroup);
        root.setPrefSize((WIDTH + 1) * TILE_SIZE, HEIGHT * TILE_SIZE);
        setHelpButton();
        root.getChildren().add(helpButton);
        drawTiles();
        stage.getScene().setRoot(root);
    }

    private void setHelpButton(){
        helpButton = new Button();
        helpButton.setText("POMOC");
        helpButton.setLayoutX(800.0);
        helpButton.setLayoutY(0.0);
        helpButton.setPrefSize(100.0,100.0);
        setHelpButtonMouseOnClickFunction();
    }

    private void setHelpButtonMouseOnClickFunction() {
        helpButton.setOnMouseClicked(e -> {
            if (currentChessmanImage != null) {
                double currentChessmanImageX = currentChessmanImage.getPrevMouseX();
                double currentChessmanImageY = currentChessmanImage.getPrevMouseY();

                var currentChessmanPossibleCaptures = currentChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                        new Pair<>(toBoard(currentChessmanImageY), toBoard(currentChessmanImageX)));

                if (currentChessmanPossibleCaptures == null)
                    currentChessmanPossibleCaptures = new HashSet<>();

                Set<Pair<Integer, Integer>> fieldsToHighLight;

                if (currentChessmanPossibleCaptures.isEmpty()) {
                    var otherChessmansThatCanCapture = findPositionsOfOtherChessmansThatCanCapture(currentChessmanImage);

                    fieldsToHighLight = decideIfHighlightOtherChessmansOrPossibleMoves(otherChessmansThatCanCapture, currentChessmanImageX,
                                                                                        currentChessmanImageY);
                    higlightFieldsToBlue(fieldsToHighLight);

                } else {
                    fieldsToHighLight = currentChessmanPossibleCaptures;
                    higlightFieldsToRed(fieldsToHighLight);
                }
            }
        });
    }

    private void higlightFieldsToRed(Set<Pair<Integer, Integer>> fieldsToHighLight){
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                                chessBoardOfChessmansImages[y][x].highlightRed();
                    }
        }
    }

    private void higlightFieldsToBlue(Set<Pair<Integer, Integer>> fieldsToHighLight){
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                        chessBoardOfChessmansImages[y][x].highlightBlue();
                    }
        }
    }

    private Set<Pair<Integer, Integer>> decideIfHighlightOtherChessmansOrPossibleMoves(Set<Pair<Integer, Integer>> otherChessmansThatCanCapture,
                                                                                       double currentChessmanX, double currentChessmanY){
        Set<Pair<Integer,Integer>> fieldsToHighLight = new HashSet<>();
        if (otherChessmansThatCanCapture.size() == 0) {
            var possibleMoves = currentChessmanImage.getChessman().getPossibleMoves(chessBoard, new Pair<>(toBoard(currentChessmanY),
                                                                                    toBoard(currentChessmanX)));
            if (possibleMoves != null && possibleMoves.size() != 0)
                fieldsToHighLight = possibleMoves;
        } else {
            fieldsToHighLight = otherChessmansThatCanCapture;
        }
        return fieldsToHighLight;
    }

    private Set<Pair<Integer, Integer>> findPositionsOfOtherChessmansThatCanCapture(ChessmanImage chessmanImage){
        Set<Pair<Integer, Integer>> positionsOfChessmansThatCanCapture = new HashSet<>();
        var chessmansWithGivenColor = chessBoard.getAllChessmansWithGivenColor(currentChessmanImage.getChessman().getColour());
        if(chessmansWithGivenColor.size() != 0){
            for (Chessman possibleChessman : chessmansWithGivenColor) {
                Pair<Integer, Integer> chessmanPosition = chessBoard.getChessmanPosition(possibleChessman);
                if (chessmanPosition != null) {
                    var captures = possibleChessman.getPossibleCaptures(chessBoard, chessmanPosition);
                    if (captures != null && captures.size() != 0) {
                        positionsOfChessmansThatCanCapture.add(chessmanPosition);
                    }
                }
            }
        }
        return positionsOfChessmansThatCanCapture;
    }

    private Tile getDrawnTile(int x, int y){
        Tile tile = new Tile((x + y) % 2 == 0, x, y);
        chessBoardOfChessmansImages[x][y] = tile;
        tileGroup.getChildren().add(tile);
        return tile;
    }

    private void drawChessman(int x, int y, Tile tile){
        var chessman = chessBoard.getChessmanAt(y,x);
        ChessmanImage chessmanImage = createChesmanImageFromChesman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y) {
        ChessmanImage chessmanImage = createChessmanImage(chessman,1, x, y);
        setChessmanImageMouseFunctions(chessmanImage);
        return chessmanImage;
    }

    private ChessmanImage createChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y){
        return new ChessmanImage(chessman, chessmanStyleNumber, x, y);
    }

    private int toBoard(double pixel){
        return (int)(pixel / TILE_SIZE);
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY){
        double prevChessmanX = chessmanImage.getPrevMouseX();
        double prevChessmanY = chessmanImage.getPrevMouseY();

        if(canGivenChessmanCaptureOtherChessman(chessmanImage,prevChessmanX,prevChessmanY, newX, newY)){
            return new MoveResult(MoveType.KILL);
        }
        else if(thereAreOtherChessmansThatCanBeat(chessmanImage))
            return new MoveResult(MoveType.NONE);

        else if (canGivenChessmanStepIntoNewTile(chessmanImage,prevChessmanX,prevChessmanY, newX, newY)){
            return new MoveResult(MoveType.NORMAL);
        }
        else{
            return new MoveResult(MoveType.NONE);
        }
    }


    private Boolean thereAreOtherChessmansThatCanBeat(ChessmanImage chessmanImage){
        return findPositionsOfOtherChessmansThatCanCapture(chessmanImage).size() != 0;
    }
    private Boolean canGivenChessmanCaptureOtherChessman(ChessmanImage givenChessmanImage, double prevChessmanX, double prevChessmanY,
                                                        int newX, int newY){
        return givenChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                new Pair<>(toBoard(prevChessmanY),toBoard(prevChessmanX))).contains(new Pair<>(newY, newX));
    }
    private Boolean canGivenChessmanStepIntoNewTile(ChessmanImage givenChessmanImage, double prevChessmanX, double prevChessmanY,
                                                    int newX, int newY){
        return givenChessmanImage.getChessman().getPossibleMoves(chessBoard,
                new Pair<>(toBoard(prevChessmanY), toBoard(prevChessmanX))).contains(new Pair<>(newY, newX));
    }

    private void unhighlightAllBoard(){
        for(int y = 0; y < HEIGHT; y++)
            for(int x = 0; x < WIDTH; x++) {
                chessBoardOfChessmansImages[x][y].removeHighlight();
            }
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage){
        chessmanImage.setOnMousePressed(e ->{
            if(canPlayerMoveChessman()) {
                unhighlightAllBoard();
                chessmanImage.setMouseX(e.getSceneX());
                chessmanImage.setMouseY(e.getSceneY());
                chessmanImage.setPrevMouseX((int) (e.getSceneX() / 100) * 100 + 7);
                chessmanImage.setPrevMouseY((int) (e.getSceneY() / 100) * 100 + 7);
                chessmanImage.setPrevChessmanY(toBoard(e.getSceneY()));
                chessmanImage.setPrevChessmanX(toBoard(e.getSceneX()));

                currentChessmanXCoordinate = toBoard(chessmanImage.getLayoutX());
                getCurrentChessmanYCoordinate = toBoard(chessmanImage.getLayoutY());
                updateCurrentChessmanImage(chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].getChessmanImage());
                chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].highlightGreen();
            }
        });

        chessmanImage.setOnMouseDragged(e ->{
            if(canPlayerMoveChessman()) {
                unhighlightAllBoard();
                chessmanImage.relocate(e.getSceneX() - 50, e.getScreenY() - 50);
            }
        });

        chessmanImage.setOnMouseReleased(e -> {
            if(canPlayerMoveChessman()) {
                int newX = toBoard(e.getSceneX());
                int newY = toBoard(e.getSceneY());
                MoveResult result = tryMove(chessmanImage, newX, newY);
                switch (result.getMoveType()) {
                    case NONE:
                        completeAbortMove(chessmanImage);
                        break;
                    case NORMAL:
                        completeNormalMove(chessmanImage, newX, newY);
                        break;
                    case KILL:
                        completeKilllMove(chessmanImage, newX, newY);
                        break;
                }
            }
        });
    }

    private void updateCurrentChessmanImage(ChessmanImage chessmanImage){
        currentChessmanImage = chessmanImage;
    }

    private void completeNormalMove(ChessmanImage chessmanImage, int newX, int newY){
        chessmanImage.move(newX, newY);
        updateBoardOfImagesAfterNormalMove(chessmanImage, newX, newY);
        currentChessmanImage = null;
    }

    private void completeAbortMove(ChessmanImage chessmanImage){
        chessmanImage.abortMove();
    }

    private void completeKilllMove(ChessmanImage chessmanImage, int newX, int newY){
        chessmanImage.move(newX, newY);
        updateBoardOfImagesAfterKillMove(chessmanImage, newX, newY);
        currentChessmanImage = null;
    }

    private Boolean canPlayerMoveChessman(){
       /* if (isUsersMove()) {
            return true;
        }
        return false;*/
       return true;
    }

    private Boolean isUsersMove() {
        ResponseEntity<Boolean> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/isUsersMove", HttpMethod.GET, null, Boolean.class);
        return responseEntity.getBody();
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
    }

    private void updateBoardOfImagesAfterNormalMove(ChessmanImage chessmanImage, int newX, int newY){
        chessBoardOfChessmansImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));
        chessBoardOfChessmansImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmans();
    }

    private void updateBoardOfImagesAfterKillMove(ChessmanImage chessmanImage, int newX, int newY){
        chessBoardOfChessmansImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));

        chessBoardOfChessmansImages[newX][newY].getChessmanImage().removeImage();
        chessBoardOfChessmansImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmans();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*ResponseEntity<String> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/gameBoard", HttpMethod.GET, null, String.class);*/
        Scene scene = new Scene(createContent("{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"" +
                                                                "],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"]," +
                                                                "\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":" +
                                                                "[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\"," +
                                                                "\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"" +
                                                                ",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"" +
                                                                "8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}"));
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
