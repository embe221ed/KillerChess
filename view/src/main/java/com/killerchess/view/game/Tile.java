package com.killerchess.view.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

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

    public void highlightGreen(){
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.GREEN);
        setStrokeWidth(5.0);
    }
    public void highlightBlue(){
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.BLUE);
        setStrokeWidth(5.0);
    }
    public void highlightRed(){
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.RED);
        setStrokeWidth(5.0);
    }
    public void removeHighlight(){
        setStrokeWidth(0.0);
    }

}
