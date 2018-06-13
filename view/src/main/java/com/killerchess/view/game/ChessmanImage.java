package com.killerchess.view.game;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

import static com.killerchess.view.game.GameBoard.TILE_SIZE;
import static com.killerchess.view.game.ImagesConstants.IMAGES_LOCAL_PATH;
import static com.killerchess.view.game.ImagesConstants.IMAGE_TYPE_PREFIX;
import static com.killerchess.view.game.ImagesConstants.PNG_FILE_TYPE_EXTENSION;

class ChessmanImage extends StackPane {

    private ChessmanType type;
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
        this.type = ChessmanType.getTypeFromSymbol(chessman.getSymbol());
        relocate(x * GameBoard.TILE_SIZE + 7, y * GameBoard.TILE_SIZE + 7);
        setChessmanImage(chessman, chessmanStyleNumber);
    }

    private void setChessmanImage(Chessman chessman, int chessmanStyleNumber) {
        if(this.type != ChessmanType.EMPTY) {
            String chessmanTypeLowerCase = type.name().toLowerCase();
            String chessmanColourLowerCase = chessman.getColour().toString().toLowerCase();
            File imageFile = getCheesmanImageFile(chessmanStyleNumber, chessmanTypeLowerCase, chessmanColourLowerCase);

            Image image = new Image(imageFile.toURI().toString());
            imageView = new ImageView();
            imageView.setImage(image);
            getChildren().add(imageView);
        }
    }

    private File getCheesmanImageFile(int chessmanStyleNumber, String chessmanTypeLowerCase, String chessmanColourLowerCase) {
        String filePath = IMAGES_LOCAL_PATH + IMAGE_TYPE_PREFIX + chessmanStyleNumber
                + "_" + chessmanColourLowerCase + "_" + chessmanTypeLowerCase + PNG_FILE_TYPE_EXTENSION;
        return new File(filePath);
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
