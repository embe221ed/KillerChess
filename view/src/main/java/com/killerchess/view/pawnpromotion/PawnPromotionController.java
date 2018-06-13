package com.killerchess.view.pawnpromotion;

import com.killerchess.view.game.ChessmanTypeEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Arrays;

import static com.killerchess.view.game.ImagesConstants.IMAGES_LOCAL_PATH;
import static com.killerchess.view.game.ImagesConstants.PNG_FILE_TYPE_EXTENSION;


public class PawnPromotionController {

    public HBox chessmenVbox;


    @FXML
    public void initialize() {
        loadChessmenIconsOnPanel();
    }

    private void loadChessmenIconsOnPanel() {
        String s = "type_1_black_king.png";
        Arrays.stream(ChessmanTypeEnum.values())
                .filter(this::isChessmanNotPawnNorEmpty)
                .forEach(chessmanTypeEnum -> loadChessmanOnPanel(chessmanTypeEnum.toString().toLowerCase()));
    }

    private boolean isChessmanNotPawnNorEmpty(ChessmanTypeEnum chessmanTypeEnum) {
        return !chessmanTypeEnum.equals(ChessmanTypeEnum.EMPTY)
                && !chessmanTypeEnum.equals(ChessmanTypeEnum.PAWN);
    }

    private void loadChessmanOnPanel(String chessman) {
        String type = "1";
        File file = new File(IMAGES_LOCAL_PATH + "type_" + type + "_black_" + chessman + PNG_FILE_TYPE_EXTENSION);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        chessmenVbox.getChildren().add(imageView);
    }

}
