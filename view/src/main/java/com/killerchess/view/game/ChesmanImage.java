package com.killerchess.view.game;

import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class ChesmanImage extends StackPane {

    private ChessmanType type;
    private ImageView imageView;
    private ChessmanType getType(){
        return type;
    }

    public ChesmanImage(ChessmanType type, ChessmanColourEnum colour, int chessmanStyleNumber, int x, int y){
        this.type = type;
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        File file;
        if(type != ChessmanType.EMPTY) {
            String chessmanTypeLowerCase = type.name().toLowerCase();
            String filePath = new StringBuilder("view/images/type_").append(chessmanStyleNumber).
                                    append("_").append(colour).append("_").append(chessmanTypeLowerCase).append(".png").toString();
            file = new File(filePath);
            setImage(file);
        }
    }

    private void setImage(File file){
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        getChildren().addAll(imageView);
    }
}
