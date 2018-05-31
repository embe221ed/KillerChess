package com.killerchess.view.game;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

import static com.killerchess.view.game.GameBoard.TILE_SIZE;

public class ChessmanImage extends StackPane {

    private ChessmanType type;
    private Chessman chessman;
    private ImageView imageView;

    private double mouseX, mouseY;
    private double prevMouseX, prevMouseY;

    private ChessmanType getType(){
        return type;
    }

    public Chessman getChessman() {
        return chessman;
    }

    public double getPrevMouseX() {
        return prevMouseX;
    }

    public double getPrevMouseY() {
        return prevMouseY;
    }

    public ChessmanImage(Chessman chessman, ChessmanType type, ChessmanColourEnum colour, int chessmanStyleNumber, int x, int y){
        this.type = type;
        this.chessman = chessman;
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        File file;
        if(type != ChessmanType.EMPTY) {
            String chessmanTypeLowerCase = type.name().toLowerCase();
            String filePath = new StringBuilder("view/images/type_").append(chessmanStyleNumber).
                    append("_").append(colour).append("_").append(chessmanTypeLowerCase).append(".png").toString();
            file = new File(filePath);
            setImage(file);

            setOnMousePressed(e ->{
                mouseX = e.getSceneX();
                mouseY = e.getSceneY();
                prevMouseX = (int)(e.getSceneX()/100) * 100 + 7;
                prevMouseY = (int)(e.getSceneY()/100) * 100 + 7;
            });
            setOnMouseDragged(e ->{
                relocate(e.getSceneX(), e.getScreenY());
            });
        }
    }

    private void createImageFromFile(ChessmanColourEnum chessmanColour,int chessmanStyleNumber){
        String chessmanTypeLowerCase = type.name().toLowerCase();
        String filePath = new StringBuilder("view/images/type_").append(chessmanStyleNumber).
                append("_").append(chessmanColour).append("_").append(chessmanTypeLowerCase).append(".png").toString();
        File file = new File(filePath);
        setImage(file);
    }

    private void setImage(File file){
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView();
        imageView.setImage(image);
        getChildren().addAll(imageView);
    }

    public void move(int x,int y){
        prevMouseX = x * TILE_SIZE;
        prevMouseY = y * TILE_SIZE;
        relocate(prevMouseX, prevMouseY);
    }

    public void abortMove(){
        relocate(prevMouseX, prevMouseY);
    }
}
