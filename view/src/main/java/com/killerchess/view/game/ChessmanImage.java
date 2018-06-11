package com.killerchess.view.game;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.lang.reflect.Constructor;

import static com.killerchess.view.game.GameBoard.TILE_SIZE;

public class ChessmanImage extends StackPane {

    private ChessmanType type;
    private Chessman chessman;
    private ImageView imageView;
    private ChessmanColourEnum colour;
    private double mouseX, mouseY;
    private double prevMouseX, prevMouseY;
    private int prevChessmanX, PrevChessmanY;

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public void setPrevMouseX(double prevMouseX) {
        this.prevMouseX = prevMouseX;
    }

    public void setPrevMouseY(double prevMouseY) {
        this.prevMouseY = prevMouseY;
    }

    private ChessmanType getType(){
        return type;
    }

    public Chessman getChessman() {
        return chessman;
    }

    public void setChessman(Chessman chessman) {
        this.chessman = chessman;
    }

    public double getPrevMouseX() {
        return prevMouseX;
    }

    public double getPrevMouseY() {
        return prevMouseY;
    }

    public ChessmanColourEnum getColour() {
        return colour;
    }

    public void setColour(ChessmanColourEnum colour) {
        this.colour = colour;
    }

    public int getPrevChessmanX() {
        return prevChessmanX;
    }

    public void setPrevChessmanX(int prevChessmanX) {
        this.prevChessmanX = prevChessmanX;
    }

    public int getPrevChessmanY() {
        return PrevChessmanY;
    }

    public void setPrevChessmanY(int prevChessmanY) {
        PrevChessmanY = prevChessmanY;
    }

    public ChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y){
        this.colour = chessman.getColour();
        this.chessman = chessman;
        this.type = ChessmanType.getTypeFromSymbol(chessman.getSymbol());
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        File file;
        if(type != ChessmanType.EMPTY) {
            String chessmanTypeLowerCase = type.name().toLowerCase();
            String filePath = new StringBuilder("view/images/type_").append(chessmanStyleNumber).
                    append("_").append(this.colour).append("_").append(chessmanTypeLowerCase).append(".png").toString();
            file = new File(filePath);
            setImage(file);
        }
    }

    public ChessmanImage(ChessmanImage chessmanImageOriginal){
        this.colour = chessmanImageOriginal.colour;
        this.type = chessmanImageOriginal.type;
        this.chessman = chessmanImageOriginal.chessman;
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
