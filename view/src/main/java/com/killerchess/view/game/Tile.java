package com.killerchess.view.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private ChesmanImage chesmanImage;

    public boolean hasPiece(){
        return chesmanImage != null;
    }

    public ChesmanImage getChesmanImage() {
        return chesmanImage;
    }

    public void setChesmanImage(ChesmanImage chesmanImage) {
        this.chesmanImage = chesmanImage;
    }

    public Tile(boolean light, int x, int y){
        setWidth(GameBoard.TILE_SIZE);
        setHeight(GameBoard.TILE_SIZE);
        relocate(x * GameBoard.TILE_SIZE, y * GameBoard.TILE_SIZE);

        setFill(light ? Color.valueOf("#f0f2a2"): Color.valueOf("#775713"));
    }
}
