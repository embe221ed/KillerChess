package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.EmptyField;
import javafx.scene.control.Button;
import com.killerchess.core.game.Game;
import com.killerchess.core.session.LocalSessionSingleton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameBoard extends Application {

    public static final int SLEEP_TIME = 1000;
    static final int TILE_SIZE = 100;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private static final String HELP_LABEL = "HELP";

    private static GameBoard instance;
    private final int IMPROPER_COORDINATE_VALUE = 100;
    private Tile[][] chessBoardOfChessmenImages = new Tile[WIDTH][HEIGHT];
    private ChessmanImage currentChessmanImage;
    private ImageView killerChessLogoImageView;
    private int currentChessmanXCoordinate = IMPROPER_COORDINATE_VALUE;
    private int getCurrentChessmanYCoordinate = IMPROPER_COORDINATE_VALUE;
    private Stage stage;

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private Button helpButton;
    private ChessBoard chessBoard;
    private Pane root;

    private StateInterpreter stateInterpreter = new StateInterpreter();
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
    private ChessmanColourEnum chessmanColour;

    private static GameBoard instance;
    private StateInterpreter stateInterpreter = new StateInterpreter();
    private Game game;
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();

    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    private Service listenerService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                public Void call() {
                    ResponseEntity<Boolean> responseEntity;
                    UriComponentsBuilder builder;
                    try {
                        do {
                            Thread.sleep(SLEEP_TIME);
                            builder = UriComponentsBuilder.fromHttpUrl(LoginController.HOST + GAME_STATE_CHANGED_PATH)
                                    .queryParam(GAME_STATE_NUMBER_PARAM,
                                            localSessionSingleton.getParameter("gameStateNumber"));
                            responseEntity = localSessionSingleton.
                                    exchange(builder.toUriString(), HttpMethod.GET, null, Boolean.class);

                        } while (!responseEntity.getBody());
                        updateGameBoard();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
    };

    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    private Parent createContent(String gameBoardStateString) {
        this.stateInterpreter = new StateInterpreter();
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);
        this.localSessionSingleton = LocalSessionSingleton.getInstance();
        this.game = new Game();

        Pane root = new Pane();
        root.setPrefSize((WIDTH + 3) * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        setHelpButton();
        setKillerChessLogoImage();
        root.getChildren().add(killerChessLogoImageView);
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
        Pane root = new Pane();
        tileGroup = new Group();
        chessmanGroup = new Group();
        root.getChildren().addAll(tileGroup, chessmanGroup);
        root.setPrefSize((WIDTH + 1) * TILE_SIZE, HEIGHT * TILE_SIZE);
        setHelpButton();
        setKillerChessLogoImage();
        root.getChildren().add(killerChessLogoImageView);
        root.getChildren().add(helpButton);
        drawTiles();
        stage.getScene().setRoot(root);
    }

    private void setHelpButton() {
        helpButton = new Button();
        helpButton.setText(HELP_LABEL);
        helpButton.setLayoutX(900.0);
        helpButton.setLayoutY(40.0);
        helpButton.setPrefSize(100.0, 100.0);
        setHelpButtonMouseOnClickFunction();
    }

    private void setKillerChessLogoImage() {
        File killerChessLogoFile = new File(IMAGES_LOCAL_PATH + KILLER_CHESS_LOGO_FILENAME);
        Image killerChessLogoImage = new Image(killerChessLogoFile.toURI().toString());
        killerChessLogoImageView = new ImageView();
        killerChessLogoImageView.setImage(killerChessLogoImage);
        killerChessLogoImageView.relocate(810, 0);
    }

    private void setHelpButtonMouseOnClickFunction() {
        helpButton.setOnMouseClicked(e -> {
            if (currentChessmanImage != null) {
                double currentChessmanImageX = currentChessmanImage.getPrevMouseX();
                double currentChessmanImageY = currentChessmanImage.getPrevMouseY();

                Pair<Integer, Integer> position = new Pair<>(convertPixelValueToBoardValue(currentChessmanImageY),
                        convertPixelValueToBoardValue(currentChessmanImageX));
                var currentChessmanPossibleCaptures = currentChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                        position);

                if (currentChessmanPossibleCaptures == null) {
                    currentChessmanPossibleCaptures = new HashSet<>();
                }

                Set<Pair<Integer, Integer>> fieldsToHighLight;

                if (currentChessmanPossibleCaptures.isEmpty()) {
                    var otherChessmenThatCanCapture = findPositionsOfOtherChessmansThatCanCapture();

                    fieldsToHighLight = decideIfHighlightOtherChessmenOrPossibleMoves(otherChessmenThatCanCapture,
                            currentChessmanImageX, currentChessmanImageY);
                    highlightFieldsToBlue(fieldsToHighLight);

                } else {
                    fieldsToHighLight = currentChessmanPossibleCaptures;
                    highlightFieldsToRed(fieldsToHighLight);
                }
            }
        });
    }

    private void highlightFieldsToRed(Set<Pair<Integer, Integer>> fieldsToHighLight) {
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                        chessBoardOfChessmenImages[y][x].highlightRed();
                    }
        }
    }

    private void highlightFieldsToBlue(Set<Pair<Integer, Integer>> fieldsToHighLight) {
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                        chessBoardOfChessmenImages[y][x].highlightBlue();
                    }
        }
    }

    private Set<Pair<Integer, Integer>> decideIfHighlightOtherChessmenOrPossibleMoves(
            Set<Pair<Integer, Integer>> otherChessmenThatCanCapture, double currentChessmanX, double currentChessmanY) {
        Set<Pair<Integer, Integer>> fieldsToHighLight = new HashSet<>();
        if (otherChessmenThatCanCapture.size() == 0) {
            Pair<Integer, Integer> position = new Pair<>(convertPixelValueToBoardValue(currentChessmanY),
                    convertPixelValueToBoardValue(currentChessmanX));
            var possibleMoves = currentChessmanImage.getChessman().getPossibleMoves(chessBoard, position);
            if (possibleMoves != null && possibleMoves.size() != 0)
                fieldsToHighLight = possibleMoves;
        } else {
            fieldsToHighLight = otherChessmenThatCanCapture;
        }
        return fieldsToHighLight;
    }

    private Set<Pair<Integer, Integer>> findPositionsOfOtherChessmansThatCanCapture() {
        var positionsOfChessmenThatCanCapture = new HashSet<Pair<Integer, Integer>>();
        var chessmenWithGivenColor =
                chessBoard.getAllChessmansWithGivenColor(currentChessmanImage.getChessman().getColour());
        if (chessmenWithGivenColor.size() != 0) {
            for (Chessman possibleChessman : chessmenWithGivenColor) {
                Pair<Integer, Integer> chessmanPosition = chessBoard.getChessmanPosition(possibleChessman);
                if (chessmanPosition != null) {
                    var captures = possibleChessman.getPossibleCaptures(chessBoard, chessmanPosition);
                    if (captures != null && captures.size() != 0) {
                        positionsOfChessmenThatCanCapture.add(chessmanPosition);
                    }
                }
            }
        }
        return positionsOfChessmenThatCanCapture;
    }

    private Tile getDrawnTile(int x, int y) {
        var tile = new Tile((x + y) % 2 == 0, x, y);
        chessBoardOfChessmenImages[x][y] = tile;
        tileGroup.getChildren().add(tile);
        return tile;
    }

    private void drawChessman(int x, int y, Tile tile) {
        var chessman = chessBoard.getChessmanAt(y, x);
        var chessmanImage = createChesmanImageFromChesman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y) {
        var chessmanImage = createChessmanImage(chessman, 1, x, y);
        setChessmanImageMouseFunctions(chessmanImage);
        return chessmanImage;
    }

    private ChessmanImage createChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y) {
        return new ChessmanImage(chessman, chessmanStyleNumber, x, y);
    }

    private int convertPixelValueToBoardValue(double pixel) {
        return (int) (pixel / TILE_SIZE);
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY) {
        double prevChessmanX = chessmanImage.getPrevMouseX();
        double prevChessmanY = chessmanImage.getPrevMouseY();

        if (canGivenChessmanCaptureOtherChessman(chessmanImage, prevChessmanX, prevChessmanY, newX, newY)) {
            return new MoveResult(MoveType.KILL);
        }
        if (thereAreOtherChessmenThatCanBeat()) {
            return new MoveResult(MoveType.NONE);
        }
        if (canGivenChessmanStepIntoNewTile(chessmanImage, prevChessmanX, prevChessmanY, newX, newY)) {
            return new MoveResult(MoveType.NORMAL);
        }
        return new MoveResult(MoveType.NONE);
    }

    private Boolean thereAreOtherChessmenThatCanBeat() {
        return findPositionsOfOtherChessmansThatCanCapture().size() != 0;
    }

    private Boolean canGivenChessmanCaptureOtherChessman(ChessmanImage givenChessmanImage, double prevChessmanX,
                                                         double prevChessmanY, int newX, int newY) {
        Pair<Integer, Integer> position = new Pair<>(convertPixelValueToBoardValue(prevChessmanY),
                convertPixelValueToBoardValue(prevChessmanX));
        Set<Pair<Integer, Integer>> possibleCaptures = givenChessmanImage.getChessman()
                .getPossibleCaptures(chessBoard, position);
        return possibleCaptures.contains(new Pair<>(newY, newX));
    }

    private Boolean canGivenChessmanStepIntoNewTile(ChessmanImage givenChessmanImage, double prevChessmanX, double prevChessmanY,
                                                    int newX, int newY) {
        Pair<Integer, Integer> position = new Pair<>(convertPixelValueToBoardValue(prevChessmanY), convertPixelValueToBoardValue(prevChessmanX));
        Set<Pair<Integer, Integer>> possibleMoves = givenChessmanImage.getChessman()
                .getPossibleMoves(chessBoard, position);
        return possibleMoves.contains(new Pair<>(newY, newX));
    }

    private void unhighlightAllBoard() {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++) {
                chessBoardOfChessmenImages[x][y].removeHighlight();
            }
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage) {
        chessmanImage.setOnMousePressed(e -> {
            if (canPlayerMoveChessman()) {
                unhighlightAllBoard();
                chessmanImage.setMouseX(e.getSceneX());
                chessmanImage.setMouseY(e.getSceneY());
                chessmanImage.setPrevMouseX((int) (e.getSceneX() / 100) * 100 + 7);
                chessmanImage.setPrevMouseY((int) (e.getSceneY() / 100) * 100 + 7);
                chessmanImage.setPrevChessmanY(convertPixelValueToBoardValue(e.getSceneY()));
                chessmanImage.setPrevChessmanX(convertPixelValueToBoardValue(e.getSceneX()));

                currentChessmanXCoordinate = convertPixelValueToBoardValue(chessmanImage.getLayoutX());
                getCurrentChessmanYCoordinate = convertPixelValueToBoardValue(chessmanImage.getLayoutY());
                Tile tile = chessBoardOfChessmenImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate];
                updateCurrentChessmanImage(tile.getChessmanImage());
                tile.highlightGreen();
            }
        });

        chessmanImage.setOnMouseDragged(e -> {
            if (canPlayerMoveChessman()) {
                unhighlightAllBoard();
                chessmanImage.relocate(e.getSceneX() - 50, e.getScreenY() - 50);
            }
        });

        chessmanImage.setOnMouseReleased(e -> {
            if (canPlayerMoveChessman()) {
                int newX = convertPixelValueToBoardValue(e.getSceneX());
                int newY = convertPixelValueToBoardValue(e.getSceneY());
                MoveResult result = tryMove(chessmanImage, newX, newY);
                switch (result.getMoveType()) {
                    case NONE:
                        completeAbortMove(chessmanImage);
                        break;
                    case NORMAL:
                        completeNormalMove(chessmanImage, newX, newY);
                        break;
                    case KILL:
                        completeKillMove(chessmanImage, newX, newY);
                        break;
                }
                updateGameState();
                waitForOpponentsMove();
            }
        });
    }

    private void waitForOpponentsMove() {
        listenerService.start();
    }

    private void updateGameState() {
        listenerService.reset();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("state", stateInterpreter.convertChessBoardToJsonBoard(chessBoard).toString());
        ResponseEntity<Integer> responseEntity = localSessionSingleton
                .exchange(LoginController.HOST + NEW_STATE_PATH, HttpMethod.POST, map, Integer.class);
        localSessionSingleton.setParameter("gameStateNumber", responseEntity.getBody().toString());
    }

    private void updateCurrentChessmanImage(ChessmanImage chessmanImage) {
        currentChessmanImage = chessmanImage;
    }

    private void completeNormalMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessmanImage.move(newX, newY);
        updateBoardOfImagesAfterNormalMove(chessmanImage, newX, newY);
        currentChessmanImage = null;
    }

    private void completeAbortMove(ChessmanImage chessmanImage) {
        chessmanImage.abortMove();
    }

    private void completeKillMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessmanImage.move(newX, newY);
        updateBoardOfImagesAfterKillMove(chessmanImage, newX, newY);
        currentChessmanImage = null;
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage) {
        return isUsersMove() && chessmanImage.getColour().equals(this.chessmanColour);
    }

    private Boolean isUsersMove() {
        ResponseEntity<Boolean> responseEntity = localSessionSingleton
                .exchange(LoginController.HOST + IS_USERS_MOVE_PATH, HttpMethod.GET, null, Boolean.class);
        return responseEntity.getBody();
    }

    private void updateChessBoardOfChessmen() {
        ArrayList<ArrayList<Chessman>> chessboardCurrentState = new ArrayList<>();
        for (int y = 0; y < HEIGHT; y++) {
            ArrayList<Chessman> currentChessmenRow = new ArrayList<>();
            for (int x = 0; x < WIDTH; x++) {
                currentChessmenRow.add(chessBoardOfChessmansImages[x][y].getChessmanImage().getChessman());
            }
            chessboardCurrentState.add(currentChessmenRow);
        }
        chessBoard = new ChessBoard(chessboardCurrentState);
    }

    private void updateBoardOfImagesAfterNormalMove(ChessmanImage chessmanImage, int newX, int newY) {
        int prevChessmanX = chessmanImage.getPrevChessmanX();
        int prevChessmanY = chessmanImage.getPrevChessmanY();
        var emptyFieldImage = new ChessmanImage(new EmptyField(chessmanImage.getColour()));
        chessBoardOfChessmenImages[prevChessmanX][prevChessmanY].setChessmanImage(emptyFieldImage);
        chessBoardOfChessmenImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmans();
    }

    private void updateBoardOfImagesAfterKillMove(ChessmanImage chessmanImage, int newX, int newY) {
        int prevChessmanX = chessmanImage.getPrevChessmanX();
        int prevChessmanY = chessmanImage.getPrevChessmanY();
        var emptyFieldImage = new ChessmanImage(new EmptyField(chessmanImage.getColour()));
        chessBoardOfChessmenImages[prevChessmanX][prevChessmanY].setChessmanImage(emptyFieldImage);

        chessBoardOfChessmenImages[newX][newY].getChessmanImage().removeImage();
        chessBoardOfChessmenImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmen();
    }

    private void getUsersChessmenColor() {
        ResponseEntity<ChessmanColourEnum> responseEntity = localSessionSingleton
                .exchange(LoginController.HOST + GET_COLOR_PATH, HttpMethod.GET, null, ChessmanColourEnum.class);
        this.chessmanColour = responseEntity.getBody();
    }

    @Override
    public void start(Stage primaryStage) {
        ResponseEntity<String> responseEntity = localSessionSingleton
                .exchange(LoginController.HOST + GAME_BOARD_PATH, HttpMethod.GET, null, String.class);
        Scene scene = new Scene(createContent(responseEntity.getBody()));
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
        getUsersChessmenColor();
        if (!isUsersMove()) {
            waitForOpponentsMove();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
