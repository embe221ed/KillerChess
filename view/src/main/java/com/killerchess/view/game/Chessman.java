package com.killerchess.view.game;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Chessman extends StackPane {

    private ChessmanType type;
    private ImageView imageView;
    private ChessmanType getType(){
        return type;
    }

    public Chessman(ChessmanType type, int x, int y){
        this.type = type;
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        File file;
        switch(type) {
            case PAWN:
                file = new File("view/images/red_pawn.png");
                setImage(file);
                break;
            case BISHOP:
                file = new File("view/images/red_bishop.png");
                setImage(file);
                break;
            case HORSE:
                file = new File("view/images/red_horse.png");
                setImage(file);
                break;
            case ROOK:
                file = new File("view/images/red_rook.png");
                setImage(file);
                break;
            case QUEEN:
                file = new File("view/images/red_queen.png");
                setImage(file);
                break;
            case KING:
                file = new File("view/images/red_king.png");
                setImage(file);
                break;
        }
    }

    private void setImage(File file){
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        getChildren().addAll(imageView);
    }
}
