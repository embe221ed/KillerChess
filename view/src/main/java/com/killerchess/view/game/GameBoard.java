package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import com.killerchess.core.chessmans.EmptyField;
import com.killerchess.core.controllers.game.GameController;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.pawnpromotion.PawnPromotionController;
import com.killerchess.view.utils.SoundPlayer;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard extends Application {

    public static final int SLEEP_TIME = 1000;
    static final int TILE_SIZE = 100;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private static GameBoard instance;
    private final int UNPROPER_COORDINATE_VALUE = 100;

    private ChessmanImage currentChessmanImage;
    private Tile[][] chessBoardOfChessmansImages = new Tile[WIDTH][HEIGHT];
    private ImageView killerChessLogoImageView;
    private Image killerChessLogoImage;
    private int currentChessmanXCoordinate = UNPROPER_COORDINATE_VALUE;
    private List<String> gameStates = new ArrayList<>();
    private boolean historyModeActive = false;

    private Stage stage;

    private Group tileGroup;
    private Group chessmanGroup;
    private Group groups;
    private Group buttonsGroup;

    private Button helpButton;
    private Button movesHistoryButton;
    private Button currentGameStateButton;
    private ChessBoard chessBoard;
    private AnchorPane root;

    private StateInterpreter stateInterpreter = new StateInterpreter();
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
    private ChessmanColourEnum chessmanColour;
    private int getCurrentChessmanYCoordinate = UNPROPER_COORDINATE_VALUE;
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
                            builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/gameStateChanged")
                                    .queryParam("gameStateNumber", localSessionSingleton.
                                            getParameter("gameStateNumber"));
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

    public static void main(String[] args) {
        launch(args);
    }

    private void drawTiles() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = getDrawnTile(x, y);
                drawChessman(x, y, tile);
            }
        }
    }

    private Parent createContent(String gameBoardStateString) {
        initGameBoard(gameBoardStateString);

        return root;
    }

    private void updateGameBoard() {
        historyModeActive = false;
        gameStates.clear();
        ResponseEntity<String> responseEntity = localSessionSingleton
                .exchange("http://localhost:8080/gameBoard", HttpMethod.GET, null, String.class);
        initGameBoard(responseEntity.getBody());
        stage.getScene().setRoot(root);
    }

    private void initNodes() {
        tileGroup = new Group();
        chessmanGroup = new Group();
        groups = new Group();
        groups.getChildren().addAll(tileGroup, chessmanGroup);
        root = new AnchorPane();
    }

    private void initGameBoard(String gameBoard) {
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoard);
        initNodes();
        root.setPrefSize((WIDTH + 4) * TILE_SIZE, HEIGHT * TILE_SIZE);
        AnchorPane.setLeftAnchor(groups, 0.0);
        setKillerChessLogoImage();
        initAllButtons();
        AnchorPane.setRightAnchor(buttonsGroup, 0.0);
        root.getChildren().addAll(groups, buttonsGroup);
        drawTiles();
    }

    private void initAllButtons() {
        helpButton = initButton(40.0, "POMOC", false);
        currentGameStateButton = initButton(280.0, "AKTUALNA", !historyModeActive);
        movesHistoryButton = initButton(160.0, "HISTORIA", historyModeActive && gameStates.isEmpty());
        setCurrentGameStateButtonOnClickFunction();
        setMovesHistoryButtonOnClickFunction();
        setHelpButtonMouseOnClickFunction();
        buttonsGroup = new Group();
        buttonsGroup.getChildren().addAll(killerChessLogoImageView, helpButton, movesHistoryButton, currentGameStateButton);
    }

    private Button initButton(double layoutY, String text, boolean disabled) {
        Button button = new Button();
        button.setText(text);
        button.setLayoutX(900.0);
        button.setLayoutY(layoutY);
        button.setPrefSize(100.0, 100.0);
        button.setDisable(disabled);
        return button;
    }

    private void setKillerChessLogoImage() {
        File killerChessLogoFile = new File("view/images/killer_chess_logo.jpg");
        killerChessLogoImage = new Image(killerChessLogoFile.toURI().toString());
        killerChessLogoImageView = new ImageView();
        killerChessLogoImageView.setImage(killerChessLogoImage);
        killerChessLogoImageView.relocate(810, 0);
    }

    private void setCurrentGameStateButtonOnClickFunction() {
        currentGameStateButton.setOnMouseClicked(e -> {
            updateGameBoard();
        });
    }

    private void setMovesHistoryButtonOnClickFunction() {
        movesHistoryButton.setOnMouseClicked(e -> {
            historyModeActive = true;
            currentGameStateButton.setDisable(false);
            updateGameStatesList();
            if (gameStates.size() > 0) {
                stage.getScene().setRoot(createContent(gameStates.remove(0)));
            }
        });
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

    private void updateGameStatesList() {
        if (gameStates.isEmpty()) {
            var parameterizedTypeReference = new ParameterizedTypeReference<List<String>>() {
            };
            ResponseEntity<List<String>> responseEntity = localSessionSingleton
                    .exchange(LoginController.HOST + GameController.GAME_BOARD_LIST_PATH,
                            HttpMethod.GET,
                            null,
                            parameterizedTypeReference);
            gameStates = responseEntity.getBody();
            gameStates.remove(0);
        }
    }

    private void higlightFieldsToRed(Set<Pair<Integer, Integer>> fieldsToHighLight) {
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                        chessBoardOfChessmansImages[y][x].highlightRed();
                    }
        }
    }

    private void higlightFieldsToBlue(Set<Pair<Integer, Integer>> fieldsToHighLight) {
        if (!fieldsToHighLight.isEmpty()) {
            for (int y = 0; y < HEIGHT; y++)
                for (int x = 0; x < WIDTH; x++)
                    if (fieldsToHighLight.contains(new Pair<>(x, y))) {
                        chessBoardOfChessmansImages[y][x].highlightBlue();
                    }
        }
    }

    private Set<Pair<Integer, Integer>> decideIfHighlightOtherChessmansOrPossibleMoves(Set<Pair<Integer, Integer>> otherChessmansThatCanCapture,
                                                                                       double currentChessmanX, double currentChessmanY) {
        Set<Pair<Integer, Integer>> fieldsToHighLight = new HashSet<>();
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

    private Set<Pair<Integer, Integer>> findPositionsOfOtherChessmansThatCanCapture(ChessmanImage chessmanImage) {
        Set<Pair<Integer, Integer>> positionsOfChessmansThatCanCapture = new HashSet<>();
        var chessmansWithGivenColor = chessBoard.getAllChessmansWithGivenColor(currentChessmanImage.getChessman().getColour());
        if (chessmansWithGivenColor.size() != 0) {
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

    private Tile getDrawnTile(int x, int y) {
        Tile tile = new Tile((x + y) % 2 == 0, x, y);
        chessBoardOfChessmansImages[x][y] = tile;
        tileGroup.getChildren().add(tile);
        return tile;
    }

    private void drawChessman(int x, int y, Tile tile) {
        var chessman = chessBoard.getChessmanAt(y, x);
        ChessmanImage chessmanImage = createChesmanImageFromChesman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y) {
        return new ChessmanImage(chessman, chessmanStyleNumber, x, y);
    }

    private ChessmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y) {
        ChessmanImage chessmanImage = createChessmanImage(chessman, 1, x, y);
        setChessmanImageMouseFunctions(chessmanImage);
        return chessmanImage;
    }

    private int toBoard(double pixel) {
        return (int) (pixel / TILE_SIZE);
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY) {
        double prevChessmanX = chessmanImage.getPrevMouseX();
        double prevChessmanY = chessmanImage.getPrevMouseY();

        if (canGivenChessmanCaptureOtherChessman(chessmanImage, prevChessmanX, prevChessmanY, newX, newY)) {
            return new MoveResult(MoveType.KILL);
        } else if (thereAreOtherChessmansThatCanBeat(chessmanImage))
            return new MoveResult(MoveType.NONE);

        else if (canGivenChessmanStepIntoNewTile(chessmanImage, prevChessmanX, prevChessmanY, newX, newY)) {
            return new MoveResult(MoveType.NORMAL);
        } else {
            return new MoveResult(MoveType.NONE);
        }
    }

    private Boolean thereAreOtherChessmansThatCanBeat(ChessmanImage chessmanImage) {
        return findPositionsOfOtherChessmansThatCanCapture(chessmanImage).size() != 0;
    }

    private Boolean canGivenChessmanCaptureOtherChessman(ChessmanImage givenChessmanImage, double prevChessmanX, double prevChessmanY,
                                                         int newX, int newY) {
        return givenChessmanImage.getChessman().getPossibleCaptures(chessBoard,
                new Pair<>(toBoard(prevChessmanY), toBoard(prevChessmanX))).contains(new Pair<>(newY, newX));
    }

    private Boolean canGivenChessmanStepIntoNewTile(ChessmanImage givenChessmanImage, double prevChessmanX, double prevChessmanY,
                                                    int newX, int newY) {
        return givenChessmanImage.getChessman().getPossibleMoves(chessBoard,
                new Pair<>(toBoard(prevChessmanY), toBoard(prevChessmanX))).contains(new Pair<>(newY, newX));
    }

    private void unhighlightAllBoard() {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++) {
                chessBoardOfChessmansImages[x][y].removeHighlight();
            }
    }

    private void waitForOpponentsMove() {
        listenerService.start();
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage) {
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
                updateCurrentChessmanImage(chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].getChessmanImage());
                chessBoardOfChessmansImages[currentChessmanXCoordinate][getCurrentChessmanYCoordinate].highlightGreen();
                new Thread(() -> {
                    SoundPlayer.playOnChessmanClick();
                }).start();

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
                int newX = toBoard(e.getSceneX());
                int newY = toBoard(e.getSceneY());
                MoveResult result = tryMove(chessmanImage, newX, newY);
                switch (result.getMoveType()) {
                    case NONE:
                        completeAbortMove(chessmanImage);
                        return;
                    case NORMAL:
                        new Thread(() -> {
                            SoundPlayer.playOnChessmanMove();
                        }).start();
                        completeNormalMove(chessmanImage, newX, newY);
                        break;
                    case KILL:
                        new Thread(() -> {
                            SoundPlayer.playOnChessmanMove();
                        }).start();
                        completeKilllMove(chessmanImage, newX, newY);
                        break;
                }
                try {
                    ChessmanTypeEnum chessmanTypeEnum = PawnPromotionController.showWindow();
                    System.out.println(chessmanTypeEnum);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                updateGameState();
                waitForOpponentsMove();
            }
        });
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

    private void updateGameState() {
        listenerService.reset();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("state", stateInterpreter.convertChessBoardToJsonBoard(chessBoard).toString());
        ResponseEntity<Integer> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/newState", HttpMethod.POST, map, Integer.class);
        localSessionSingleton.setParameter("gameStateNumber", responseEntity.getBody().toString());
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage) {
        return !historyModeActive && isUsersMove() && chessmanImage.getColour().equals(this.chessmanColour);
    }

    private void completeKilllMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessmanImage.move(newX, newY);
        updateBoardOfImagesAfterKillMove(chessmanImage, newX, newY);
        currentChessmanImage = null;
    }

    private Boolean isUsersMove() {
        ResponseEntity<Boolean> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/isUsersMove", HttpMethod.GET, null, Boolean.class);
        return responseEntity.getBody();
    }

    private void updateChessBoardOfChessmans() {
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

    private void updateBoardOfImagesAfterNormalMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessBoardOfChessmansImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));
        chessBoardOfChessmansImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmans();
    }

    private void updateBoardOfImagesAfterKillMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessBoardOfChessmansImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));

        chessBoardOfChessmansImages[newX][newY].getChessmanImage().removeImage();
        chessBoardOfChessmansImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmans();
    }

    private void getUsersChessmenColor() {
        ResponseEntity<ChessmanColourEnum> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/getColor", HttpMethod.GET, null, ChessmanColourEnum.class);
        this.chessmanColour = responseEntity.getBody();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResponseEntity<String> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/gameBoard", HttpMethod.GET, null, String.class);
        Scene scene = new Scene(createContent(responseEntity.getBody()));
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
        getUsersChessmenColor();
        if (!isUsersMove()) {
            waitForOpponentsMove();
        }
    }
}