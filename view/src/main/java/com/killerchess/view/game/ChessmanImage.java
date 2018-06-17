package com.killerchess.view.game;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import static com.killerchess.view.game.GameBoard.TILE_SIZE;
import static com.killerchess.view.game.ImagesConstants.*;

class ChessmanImage extends StackPane {

    private ChessmanTypeEnum type;
    private static int CHESSMAN_SIZE = (int) (GameBoard.TILE_SIZE * 0.9);

    private Chessman chessman;
    private ImageView imageView;
    private ChessmanColourEnum colour;
    private double mouseX, mouseY;
    private double prevMouseX, prevMouseY;
    private int prevChessmanX, PrevChessmanY;

    void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    void setPrevMouseX(double prevMouseX) {
        this.prevMouseX = prevMouseX;
    }

    void setPrevMouseY(double prevMouseY) {
        this.prevMouseY = prevMouseY;
    }

    Chessman getChessman() {
        return chessman;
    }

    double getPrevMouseX() {
        return prevMouseX;
    }

    double getPrevMouseY() {
        return prevMouseY;
    }

    ChessmanColourEnum getColour() {
        return colour;
    }

    int getPrevChessmanX() {
        return prevChessmanX;
    }

    void setPrevChessmanX(int prevChessmanX) {
        this.prevChessmanX = prevChessmanX;
    }

    int getPrevChessmanY() {
        return PrevChessmanY;
    }

    void setPrevChessmanY(int prevChessmanY) {
        PrevChessmanY = prevChessmanY;
    }

    ChessmanImage(Chessman chessman){
        this.colour = chessman.getColour();
        this.chessman = chessman;
    }

    ChessmanImage(Chessman chessman, int chessmanStyleNumber, int x, int y){
        this.colour = chessman.getColour();
        this.chessman = chessman;
        this.type = ChessmanTypeEnum.getTypeFromSymbol(chessman.getSymbol());
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        setChessmanImage(chessman, chessmanStyleNumber);
    }

    private void setChessmanImage(Chessman chessman, int chessmanStyleNumber) {
        if(this.type != ChessmanTypeEnum.EMPTY) {
            String chessmanTypeLowerCase = type.name().toLowerCase();
            String chessmanColourLowerCase = chessman.getColour().toString().toLowerCase();
            String imagePath = getChessmanImageFilePath(chessmanStyleNumber, chessmanTypeLowerCase,
                    chessmanColourLowerCase);
            Image image = new Image(imagePath, CHESSMAN_SIZE, CHESSMAN_SIZE, false, false);
            imageView = new ImageView();
            imageView.setImage(image);
            getChildren().add(imageView);
        }
    }

    private String getChessmanImageFilePath(int chessmanStyleNumber, String chessmanTypeLowerCase, String
            chessmanColourLowerCase) {
        return IMAGES_LOCAL_PATH + IMAGE_TYPE_PREFIX + chessmanStyleNumber
                + "_" + chessmanColourLowerCase + "_" + chessmanTypeLowerCase + PNG_FILE_TYPE_EXTENSION;
    }

    void move(int x, int y){
        prevMouseX = x * TILE_SIZE + 7;
        prevMouseY = y * TILE_SIZE + 7;
        relocate(prevMouseX, prevMouseY);
    }

    void abortMove(){
        relocate(prevMouseX, prevMouseY);
    }

    void removeImage(){
        imageView.setImage(null);
    }
}
