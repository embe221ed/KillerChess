package com.killerchess.view.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Chessman chessman;

    public boolean hasPiece(){
        return chessman != null;
    }

    public Chessman getChessman() {
        return chessman;
    }

    public void setChessman(Chessman chessman) {
        this.chessman = chessman;
    }

    public Tile(boolean light, int x, int y){
        setWidth(GameBoard.TILE_SIZE);
        setHeight(GameBoard.TILE_SIZE);
        relocate(x * GameBoard.TILE_SIZE, y * GameBoard.TILE_SIZE);

        setFill(light ? Color.valueOf("#f0f2a2"): Color.valueOf("#775713"));
    }
}
