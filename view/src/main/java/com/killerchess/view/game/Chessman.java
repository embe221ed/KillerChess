package com.killerchess.view.game;

import javafx.scene.layout.StackPane;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Chessman extends StackPane {

    private ChessmanType type;

    private ChessmanType getType(){
        return type;
    }

    public Chessman(ChessmanType type, int x, int y){
        this.type = type;
        relocate(x * GameBoard.TILE_SIZE, y * GameBoard.TILE_SIZE);

        switch(type){
            case KING:
                File file = new File("");
        }

    }
}
