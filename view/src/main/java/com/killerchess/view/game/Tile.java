package com.killerchess.view.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private ChessmanImage chessmanImage;

    public boolean hasPiece(){
        return chessmanImage != null;
    }

    public ChessmanImage getChessmanImage() {
        return chessmanImage;
    }

    public void setChessmanImage(ChessmanImage chessmanImage) {
        this.chessmanImage = chessmanImage;
    }

    public Tile(boolean light, int x, int y){
        setWidth(GameBoard.TILE_SIZE);
        setHeight(GameBoard.TILE_SIZE);
        relocate(x * GameBoard.TILE_SIZE, y * GameBoard.TILE_SIZE);

        setFill(light ? Color.valueOf("#f0f2a2"): Color.valueOf("#775713"));
    }
}
