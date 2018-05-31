package com.killerchess.view.game;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GameBoard extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] chessboard = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group chessmanGroup = new Group();

    private Parent createContent(String gameBoardStateString){
        StateInterpreter stateInterpreter = new StateInterpreter();
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(gameBoardStateString);

        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, chessmanGroup);
        for(int y = 0; y < HEIGHT; y++){
            for(int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                chessboard[x][y] = tile;
                tileGroup.getChildren().add(tile);

                var chessman = chessBoard.getChessmanAt(y,x);
                ChesmanImage chesmanImage = createChesmanImageFromChesman(chessman, x, y);
                tile.setChesmanImage(chesmanImage);
                chessmanGroup.getChildren().add(chesmanImage);
            }
        }

        return root;
    }

    private ChesmanImage createChesmanImageFromChesman(Chessman chessman, int x, int y){
        ChessmanType chessmanType = ChessmanType.getTypeFromSymbol(chessman.getSymbol());
        ChessmanColourEnum chessmanColour = chessman.getColour();
        return createChessmanImage(chessmanType, chessmanColour,1,  x, y);
    }

    private ChesmanImage createChessmanImage(ChessmanType type, ChessmanColourEnum colour, int chessmanStyleNumber, int x, int y){
        return new ChesmanImage(type, colour, chessmanStyleNumber, x, y);
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
