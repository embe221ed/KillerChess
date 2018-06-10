package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import com.killerchess.core.game.Game;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.core.user.User;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameBoard extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] chessboard = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private ChessBoard chessBoard;

    private StateInterpreter stateInterpreter;
    private Game game;
    LocalSessionSingleton localSessionSingleton;

    private Parent createContent(String gameBoardStateString){
        this.stateInterpreter = new StateInterpreter();
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);
        this.localSessionSingleton = LocalSessionSingleton.getInstance();
        this.game = new Game();

        User host = new User();
        host.setLogin("host");

        User guest = new User();
        guest.setLogin("guest");

        game.setHost(host);
        game.setGuest(guest);

        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        for(int y = 0; y < HEIGHT; y++){
            for(int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                chessboard[x][y] = tile;
                tileGroup.getChildren().add(tile);

                var chessman = chessBoard.getChessmanAt(y,x);
                ChessmanImage chessmanImage = createChesmanImageFromChesman(chessman, x, y);
                tile.setChessmanImage(chessmanImage);
                chessmanGroup.getChildren().add(chessmanImage);
            }
        }

        return root;
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
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    private MoveResult tryMove(ChessmanImage chessmanImage, int newX, int newY){
        double prevChessmanX = chessmanImage.getPrevMouseX();
        double prevChessmanY = chessmanImage.getPrevMouseY();
        if (chessmanImage.getChessman().getPossibleMoves(chessBoard, new Pair<>(toBoard(prevChessmanY),toBoard(prevChessmanX))).contains(new Pair<>(newY, newX))){
            return new MoveResult(MoveType.NORMAL);
        }
        else{
            return new MoveResult(MoveType.NONE);
        }
    }

    private void setChessmanImageMouseFunctions(ChessmanImage chessmanImage){
        chessmanImage.setOnMousePressed(e ->{
            if(canPlayerMoveChessman(chessmanImage)) {
                chessmanImage.setMouseX(e.getSceneX());
                chessmanImage.setMouseY(e.getSceneY());
                chessmanImage.setPrevMouseX((int) (e.getSceneX() / 100) * 100 + 7);
                chessmanImage.setPrevMouseY((int) (e.getSceneY() / 100) * 100 + 7);
            }
        });
        chessmanImage.setOnMouseDragged(e ->{
            if(canPlayerMoveChessman(chessmanImage)) {
                chessmanImage.relocate(e.getSceneX(), e.getScreenY());
            }
        });

        chessmanImage.setOnMouseReleased(e -> {
            if(canPlayerMoveChessman(chessmanImage)) {
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
                        break;
                    case KILL:

                }
            }
        });
    }

    private Boolean canPlayerMoveChessman(ChessmanImage chessmanImage){
       // String userLogin = localSessionSingleton.getParameter("username");
       String userLogin = "guest";
        //guest always plays with black
        if(chessmanImage.getColour().getSymbol() == 'B' && game.getGuest().getLogin() == userLogin)
            return true;

        //host always plays with white
        else if(chessmanImage.getColour().getSymbol() == 'W' && game.getHost().getLogin() == userLogin)
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
