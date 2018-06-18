package com.killerchess.view.pawnpromotion;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.game.ChessmanTypeEnum;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.killerchess.view.game.ImagesConstants.IMAGES_LOCAL_PATH;
import static com.killerchess.view.game.ImagesConstants.PNG_FILE_TYPE_EXTENSION;
import static javafx.application.Application.launch;


public class PawnPromotionController {

    @FXML
    public HBox chessmenHbox;

    private ChessmanTypeEnum chosenChessmanToPromote;
    private Stage window;

    public void main(String[] args) {
        launch(args);
    }


    public Character getChessmanSymbolToPromoteFromShownWindow() {
        FXMLLoader loader = new FXMLLoader(PawnPromotionController.class.getResource("/pawn_promotion.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(Objects.requireNonNull(root), 640, 260);

        window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);

        loadNamespaceIntoVariables(loader);
        loadChessmenIconsOnPanel();

        window.showAndWait();

        return chosenChessmanToPromote.getSymbolFromType();
    }

    private void loadChessmenIconsOnPanel() {
        Arrays.stream(ChessmanTypeEnum.values())
                .filter(this::isChessmanNotPawnNorEmpty)
                .forEach(this::loadChessmanOnPanel);
    }

    private boolean isChessmanNotPawnNorEmpty(ChessmanTypeEnum chessmanTypeEnum) {
        return !chessmanTypeEnum.equals(ChessmanTypeEnum.EMPTY)
                && !chessmanTypeEnum.equals(ChessmanTypeEnum.PAWN);
    }

    private void loadChessmanOnPanel(ChessmanTypeEnum chessman) {
        String type = LocalSessionSingleton.getInstance().getParameter("template_number");
        Image image = new Image(IMAGES_LOCAL_PATH + "type_" + type + "_black_" + chessman.toString().toLowerCase()
                + PNG_FILE_TYPE_EXTENSION, 100.0, 100.0, true, true);
        ImageView imageView = new ImageView(image);

        imageView.setOnMouseClicked(event -> {
            chosenChessmanToPromote = chessman;
            window.close();
        });

        chessmenHbox.getChildren().add(imageView);
    }

    private void loadNamespaceIntoVariables(FXMLLoader loader) {
        ObservableMap<String, Object> namespace = loader.getNamespace();
        chessmenHbox = (HBox) namespace.get("chessmenHbox");

    }
}
