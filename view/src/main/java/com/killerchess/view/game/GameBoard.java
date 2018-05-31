package com.killerchess.view.game;

import com.killerchess.core.chessboard.ChessBoard;
import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
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

    private Parent createContent(String gameBoardStateString){
        this.stateInterpreter = new StateInterpreter();
        this.chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);

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

    private ChessmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y){
        ChessmanImage chessmanImage = createChessmanImage(chessman,1,  x, y);
        /*chessmanImage.setOnMouseReleased(e -> {
            int newX = toBoard(chessmanImage.getLayoutX());
            int newY = toBoard(chessmanImage.getLayoutY());

            MoveResult result = tryMove(chessmanImage, newX, newY);

            switch(result.getMoveType()){
                case NONE:
                    chessmanImage.abortMove();
                    break;
                case NORMAL:
                    chessmanImage.move(newX, newY);
                    break;
                case KILL:

            }
        });*/

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
        if (chessmanImage.getChessman().getPossibleMoves(chessBoard, new Pair<>(toBoard(prevChessmanX),toBoard(prevChessmanY))).contains(new Pair<>(newX, newY))){
            System.out.println("CHUUUUUUUJ");
            return new MoveResult(MoveType.NORMAL);
        }
        else{
            return new MoveResult(MoveType.NONE);
        }
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
