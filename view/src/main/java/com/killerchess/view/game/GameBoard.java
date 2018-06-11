package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.game.Game;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.core.user.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Stage stage;
    private Button availableMovesButton;
    private Button availableCapturesButton;
    private ChessBoard chessBoard;
    private Pane root;
    private StateInterpreter stateInterpreter = new StateInterpreter();
    private Game game;
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Runnable listener = () -> {
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

    private Parent createContent(String gameBoardStateString) {
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);
        this.game = new Game();

        User host = new User();
        host.setLogin("host");

        User guest = new User();
        guest.setLogin("guest");

        game.setHost(host);
        game.setGuest(guest);

        root = new Pane();
        root.setPrefSize((WIDTH + 1) * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        setAvailableMovesButton();
        root.getChildren().add(availableMovesButton);
        drawTiles();
        return root;
    }

    private void drawTiles() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = drawAndReturnTile(x, y);
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
        setAvailableMovesButton();
        root.getChildren().add(availableMovesButton);
        drawTiles();
        stage.getScene().setRoot(root);
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
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        if (currentChessmanImage.getChessman().getPossibleMoves(chessBoard,
                                new Pair<>(toBoard(currentChessmanY), toBoard(currentChessmanX))).contains(new Pair<>(x, y))) {
                            chessboard[y][x].highlightBlue();
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
        ChessmanImage chessmanImage = createChesmanImageFromChesman(chessman, x, y);
        tile.setChessmanImage(chessmanImage);
        chessmanGroup.getChildren().add(chessmanImage);
    }

    private ChessmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y) {
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
                        return;
                    case NORMAL:
                        chessmanImage.move(newX, newY);
                        chessboard[newX][newY].setChessmanImage(chessmanImage);
                        break;
                    case KILL:
                        //   chessmanImage
                }
                moveChessman(currentChessmanXCoordinate, getCurrentChessmanYCoordinate, newX, newY);
//                executorService.submit(listener);
                Platform.runLater(listener);
            }
        });
    }

    private void moveChessman(int prevCoordX, int prevCoordY, int newCoordX, int newCoordY) {
        chessBoard.moveChessman(prevCoordX, prevCoordY, newCoordX, newCoordY);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("state", stateInterpreter.convertChessBoardToJsonBoard(chessBoard).toString());
        ResponseEntity<Integer> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/newState", HttpMethod.POST, map, Integer.class);
        localSessionSingleton.setParameter("gameStateNumber", responseEntity.getBody().toString());
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage) {
//        String userLogin = localSessionSingleton.getParameter("username");
//        String userLogin = "guest";
        //guest always plays with black
        if (isUsersMove()) {
            return true;
        }
        return false;
//        if (chessmanImage.getColour().getSymbol() == 'B' && game.getGuest().getLogin() == userLogin)
//            return true;
//
//            //host always plays with white
//        else if (chessmanImage.getColour().getSymbol() == 'W' && game.getHost().getLogin() == userLogin)
//            return true;
//        return false;
    }

    private Boolean isUsersMove() {
        ResponseEntity<Boolean> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/isUsersMove", HttpMethod.GET, null, Boolean.class);
        return responseEntity.getBody();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResponseEntity<String> responseEntity = localSessionSingleton.
                exchange("http://localhost:8080/gameBoard", HttpMethod.GET, null, String.class);
        Scene scene = new Scene(createContent(responseEntity.getBody()));

        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
