package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.ColourNotFoundException;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import com.killerchess.core.chessmans.EmptyField;
import com.killerchess.core.controllers.app.RankingController;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard extends Application {

    public static final int SLEEP_TIME = 1000;
    static final int TILE_SIZE = 80;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final double LOGO_WIDTH_HEIGHT_RATIO = 3.52;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_WIDTH = 100;

    private static GameBoard instance;
    private final int IMPROPER_COORDINATE_VALUE = 100;

    private ChessmanImage currentChessmanImage;
    private Tile[][] chessBoardOfChessmenImages = new Tile[WIDTH][HEIGHT];
    private Image killerChessLogoImage;
    private ImageView killerChessLogoImageView;
    private int currentChessmanXCoordinate = IMPROPER_COORDINATE_VALUE;
    private List<String> gameStates = new ArrayList<>();
    private boolean historyModeActive = false;
    private boolean gameFinished = false;

    private Stage stage;

    private Group tileGroup;
    private Group chessmanGroup;
    private Group groups;
    private Group buttonsGroup;

    private Button helpButton;
    private Button movesHistoryButton;
    private Button currentGameStateButton;
    private ChessBoard chessBoard;
    private HBox root;

    private StateInterpreter stateInterpreter = new StateInterpreter();
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
    private ChessmanColourEnum chessmanColour;
    private int currentChessmanYCoordinate = IMPROPER_COORDINATE_VALUE;
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
                            builder = UriComponentsBuilder.fromHttpUrl(LoginController.HOST + GameController.GAME_STATE_CHANGED_PATH)
                                    .queryParam(GameController.GAME_STATE_NUMBER_PARAM, localSessionSingleton.
                                            getParameter(GameController.GAME_STATE_NUMBER_PARAM));
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
                .exchange(LoginController.HOST + GameController.GAME_BOARD_PATH, HttpMethod.GET, null, String.class);
        initGameBoard(responseEntity.getBody());
        stage.getScene().setRoot(root);
        if (!gameFinished) {
            if (responseEntity.getStatusCode().equals(HttpStatus.CREATED))
                finishGame();
            else if (chessBoard.isStalemate(chessmanColour))
                endOfGameStalemate();
        }
    }

    private void initNodes() {
        tileGroup = new Group();
        chessmanGroup = new Group();
        groups = new Group();
        groups.getChildren().addAll(tileGroup, chessmanGroup);
        root = new HBox();
    }

    private void initGameBoard(String gameBoard) {
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoard);
        initNodes();
        setKillerChessLogoImage();
        root.setMinWidth(WIDTH * TILE_SIZE + killerChessLogoImage.getWidth());
        initAllButtons();
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
        button.setLayoutX(TILE_SIZE * WIDTH + ((killerChessLogoImage.getWidth() - BUTTON_WIDTH) / 2));
        button.setLayoutY(layoutY);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setDisable(disabled);
        return button;
    }

    private void finishGame() {
        helpButton.setDisable(true);
        gameFinished = true;
    }

    private void setKillerChessLogoImage() {
        File killerChessLogoFile = new File("view/images/killer_chess_logo.jpg");
        killerChessLogoImage = new Image(killerChessLogoFile.toURI().toString(), TILE_SIZE * HEIGHT / LOGO_WIDTH_HEIGHT_RATIO,
                TILE_SIZE * HEIGHT, false, false);
        killerChessLogoImageView = new ImageView();
        killerChessLogoImageView.setImage(killerChessLogoImage);
        killerChessLogoImageView.relocate(TILE_SIZE * WIDTH, 0);
    }

    private void setCurrentGameStateButtonOnClickFunction() {
        currentGameStateButton.setOnMouseClicked(e -> updateGameBoard());
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
                    var otherChessmenThatCanCapture = findPositionsOfOtherChessmenThatCanCapture(currentChessmanImage);

                    fieldsToHighLight = decideIfHighlightOtherChessmenOrPossibleMoves(otherChessmenThatCanCapture, currentChessmanImageX,
                            currentChessmanImageY);
                    highlightFieldsToBlue(fieldsToHighLight);

                } else {
                    fieldsToHighLight = currentChessmanPossibleCaptures;
                    highlightFieldsToRed(fieldsToHighLight);
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

    private Set<Pair<Integer, Integer>> decideIfHighlightOtherChessmenOrPossibleMoves(Set<Pair<Integer, Integer>> otherChessmenThatCanCapture,
                                                                                      double currentChessmanX, double currentChessmanY) {
        Set<Pair<Integer, Integer>> fieldsToHighLight = new HashSet<>();
        if (otherChessmenThatCanCapture.size() == 0) {
            var possibleMoves = currentChessmanImage.getChessman().getPossibleMoves(chessBoard, new Pair<>(toBoard(currentChessmanY),
                    toBoard(currentChessmanX)));
            if (possibleMoves != null && possibleMoves.size() != 0)
                fieldsToHighLight = possibleMoves;
        } else {
            fieldsToHighLight = otherChessmenThatCanCapture;
        }
        return fieldsToHighLight;
    }

    private Set<Pair<Integer, Integer>> findPositionsOfOtherChessmenThatCanCapture(ChessmanImage chessmanImage) {
        Set<Pair<Integer, Integer>> positionsOfChessmenThatCanCapture = new HashSet<>();
        var chessmenWithGivenColor = chessBoard.getAllChessmenWithGivenColor(currentChessmanImage.getChessman().getColour());
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
        Tile tile = new Tile((x + y) % 2 == 0, x, y);
        chessBoardOfChessmenImages[x][y] = tile;
        tileGroup.getChildren().add(tile);
        return tile;
    }

    private void drawChessman(int x, int y, Tile tile) {
        var chessman = chessBoard.getChessmanAt(y, x);
        ChessmanImage chessmanImage = createChessmanImageFromChessman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y) {
        return new ChessmanImage(chessman, chessmanStyleNumber, x, y);
    }

    private ChessmanImage createChessmanImageFromChessman(Chessman chessman, int x, int y) {
        int chessmanStyleNumber = Integer.parseInt(localSessionSingleton.getParameter("template_number"));
        ChessmanImage chessmanImage = createChessmanImage(chessman, chessmanStyleNumber, x, y);
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
        } else if (thereAreOtherChessmenThatCanBeat(chessmanImage))
            return new MoveResult(MoveType.NONE);

        else if (canGivenChessmanStepIntoNewTile(chessmanImage, prevChessmanX, prevChessmanY, newX, newY)) {
            return new MoveResult(MoveType.NORMAL);
        } else {
            return new MoveResult(MoveType.NONE);
        }
    }

    private Boolean thereAreOtherChessmenThatCanBeat(ChessmanImage chessmanImage) {
        return findPositionsOfOtherChessmenThatCanCapture(chessmanImage).size() != 0;
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
                chessBoardOfChessmenImages[x][y].removeHighlight();
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
                chessmanImage.setPrevMouseX((int) (e.getSceneX() / TILE_SIZE) * TILE_SIZE + 7);
                chessmanImage.setPrevMouseY((int) (e.getSceneY() / TILE_SIZE) * TILE_SIZE + 7);
                chessmanImage.setPrevChessmanY(toBoard(e.getSceneY()));
                chessmanImage.setPrevChessmanX(toBoard(e.getSceneX()));

                currentChessmanXCoordinate = toBoard(chessmanImage.getLayoutX());
                currentChessmanYCoordinate = toBoard(chessmanImage.getLayoutY());
                updateCurrentChessmanImage(chessBoardOfChessmenImages[currentChessmanXCoordinate][currentChessmanYCoordinate].getChessmanImage());
                chessBoardOfChessmenImages[currentChessmanXCoordinate][currentChessmanYCoordinate].highlightGreen();
                new Thread(SoundPlayer::playOnChessmanClick).start();

            }
        });

        chessmanImage.setOnMouseDragged(e -> {
            if (canPlayerMoveChessman(chessmanImage)) {
                unhighlightAllBoard();
                chessmanImage.relocate(e.getSceneX() - TILE_SIZE / 2, e.getSceneY() - TILE_SIZE / 2);
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
                        new Thread(SoundPlayer::playOnChessmanMove).start();
                        completeNormalMove(chessmanImage, newX, newY);
                        break;
                    case KILL:
                        new Thread(SoundPlayer::playOnChessmanMove).start();
                        completeKillMove(chessmanImage, newX, newY);
                        break;
                }
//                checkIfOneOfPawnsShouldBePromoted();
                checkIfGameEnded();
                if (!gameFinished) {
                    updateGameState();
                    waitForOpponentsMove();
                }
            }
        });
    }

    private void checkIfGameEnded() {
        var areMovesPossibleMap = chessBoard.checkIfBothUsersHaveChessmen();
        if (!(areMovesPossibleMap.get(ChessmanColourEnum.BLACK) && areMovesPossibleMap.get(ChessmanColourEnum.WHITE))) {
            endOfGame();
        }
    }

    private void checkIfOneOfPawnsShouldBePromoted() {
        for (int x = 0; x < WIDTH; x++) {
            checkIfWhitePawnShouldBePromoted(chessBoardOfChessmenImages[x][HEIGHT - 1]);
            checkIfBlackPawnShouldBePromoted(chessBoardOfChessmenImages[x][0]);
        }
    }

    private void checkIfWhitePawnShouldBePromoted(Tile chessmanTile) {
        Chessman chessman = chessmanTile.getChessmanImage().getChessman();
        ChessmanColourEnum whiteColour = ChessmanColourEnum.WHITE;
        if (chessman.getSymbol().equals('P') && chessman.getColour().equals(whiteColour)) {
            try {
                var chessmanSymbolToSubstitutePawn =
                        new PawnPromotionController().getChessmanSymbolToPromoteFromShownWindow();
                var chessmanStringValueToSubstitutePawn = chessmanSymbolToSubstitutePawn.toString()
                        + whiteColour.getSymbol();
                var chessmanToSubstitutePawn = Chessman.createChessman(chessmanStringValueToSubstitutePawn);
                var chessmanImageToSubstitutePawn = new ChessmanImage(chessmanToSubstitutePawn);
                chessmanTile.setChessmanImage(chessmanImageToSubstitutePawn);
            } catch (ColourNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkIfBlackPawnShouldBePromoted(Tile chessmanTile) {
        Chessman chessman = chessmanTile.getChessmanImage().getChessman();
        if (chessman.getSymbol().equals('P') && chessman.getColour().equals(ChessmanColourEnum.BLACK)) {

        }
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

    private void updateGameState() {
        listenerService.reset();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("state", stateInterpreter.convertChessBoardToJsonBoard(chessBoard).toString());
        ResponseEntity<Integer> responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.NEW_STATE_PATH, HttpMethod.POST, map, Integer.class);
        localSessionSingleton.setParameter(GameController.GAME_STATE_NUMBER_PARAM, responseEntity.getBody().toString());
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage) {
        return !historyModeActive && isUsersMove() && chessmanImage.getColour().equals(this.chessmanColour);
    }

    private void endOfGame() {
        updateGameState();
        var responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.FINISH_GAME_PATH,
                        HttpMethod.GET,
                        null,
                        ResponseEntity.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            localSessionSingleton.exchange(LoginController.HOST + RankingController.UPDATE_USER_RANKING_PATH,
                    HttpMethod.GET,
                    null,
                    ResponseEntity.class);
            finishGame();
        }
    }

    private void endOfGameStalemate() {
        var responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.FINISH_GAME_PATH,
                        HttpMethod.GET,
                        null,
                        ResponseEntity.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            localSessionSingleton.exchange(LoginController.HOST + RankingController.UPDATE_USER_RANKING_STALEMATE_PATH,
                    HttpMethod.GET,
                    null,
                    ResponseEntity.class);
            finishGame();
        }
    }

    private Boolean isUsersMove() {
        ResponseEntity<Boolean> responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.IS_USERS_MOVE_PATH, HttpMethod.GET, null, Boolean.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK))
            return responseEntity.getBody();
        finishGame();
        return false;
    }

    private void updateChessBoardOfChessmen() {
        ArrayList<ArrayList<Chessman>> chessboardCurrentState = new ArrayList<>();
        for (int y = 0; y < HEIGHT; y++) {
            ArrayList<Chessman> currentChessmenRow = new ArrayList<>();
            for (int x = 0; x < WIDTH; x++) {
                currentChessmenRow.add(chessBoardOfChessmenImages[x][y].getChessmanImage().getChessman());
            }
            chessboardCurrentState.add(currentChessmenRow);
        }
        chessBoard = new ChessBoard(chessboardCurrentState);
    }

    private void updateBoardOfImagesAfterNormalMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessBoardOfChessmenImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));
        chessBoardOfChessmenImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmen();
    }

    private void updateBoardOfImagesAfterKillMove(ChessmanImage chessmanImage, int newX, int newY) {
        chessBoardOfChessmenImages[chessmanImage.getPrevChessmanX()][chessmanImage.getPrevChessmanY()].
                setChessmanImage(new ChessmanImage(new EmptyField(chessmanImage.getColour())));

        chessBoardOfChessmenImages[newX][newY].getChessmanImage().removeImage();
        chessBoardOfChessmenImages[newX][newY].setChessmanImage(chessmanImage);
        updateChessBoardOfChessmen();
    }

    private void getUsersChessmenColor() {
        ResponseEntity<ChessmanColourEnum> responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.GET_COLOR_PATH, HttpMethod.GET, null, ChessmanColourEnum.class);
        this.chessmanColour = responseEntity.getBody();
    }

    @Override
    public void start(Stage primaryStage) {
        getUsersChessmenColor();
        this.stage = primaryStage;
        stage.setResizable(false);
        ResponseEntity<String> responseEntity = localSessionSingleton.
                exchange(LoginController.HOST + GameController.GAME_BOARD_PATH, HttpMethod.GET, null, String.class);
        Scene scene = new Scene(createContent(responseEntity.getBody()));
        primaryStage.setScene(scene);
        primaryStage.show();
        if (!isUsersMove()) {
            waitForOpponentsMove();
        }
    }
}