package com.killerchess.view.pawnpromotion;

import com.killerchess.view.game.ChessmanTypeEnum;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

import static com.killerchess.view.game.ImagesConstants.IMAGES_LOCAL_PATH;
import static com.killerchess.view.game.ImagesConstants.PNG_FILE_TYPE_EXTENSION;
import static javafx.application.Application.launch;


public class PawnPromotionController {

    @FXML
    public static HBox chessmenHbox;

    private static ChessmanTypeEnum chosenChessmanToPromote;

    private static Stage window;
    private static PawnPromotionController instance;

    public static PawnPromotionController getInstance() {
        if (instance == null) {
            instance = new PawnPromotionController();
        }
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ChessmanTypeEnum showWindowAndGetChessmanSymbol() throws Exception {
        FXMLLoader loader = new FXMLLoader(PawnPromotionController.class.getResource("/pawn_promotion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 260);

        window = new Stage();
        window.setScene(scene);
        window.show();

        loadNamespaceIntoVariables(loader);
        loadChessmenIconsOnPanel();

        return chosenChessmanToPromote;
    }

    private static void loadChessmenIconsOnPanel() {
        Arrays.stream(ChessmanTypeEnum.values())
                .filter(PawnPromotionController::isChessmanNotPawnNorEmpty)
                .forEach(PawnPromotionController::loadChessmanOnPanel);
    }

    private static boolean isChessmanNotPawnNorEmpty(ChessmanTypeEnum chessmanTypeEnum) {
        return !chessmanTypeEnum.equals(ChessmanTypeEnum.EMPTY)
                && !chessmanTypeEnum.equals(ChessmanTypeEnum.PAWN);
    }

    private static void loadChessmanOnPanel(ChessmanTypeEnum chessman) {
        //TODO AK implement choosing type of images
        String type = "1";
        File file = new File(IMAGES_LOCAL_PATH + "type_" + type + "_black_" + chessman.toString().toLowerCase()
                + PNG_FILE_TYPE_EXTENSION);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);

        imageView.setOnMouseClicked(event -> {
            chosenChessmanToPromote = chessman;
//            changeSceneToGameBoard();
            window.close();
        });

        chessmenHbox.getChildren().add(imageView);
    }

    private static void loadNamespaceIntoVariables(FXMLLoader loader) {
        ObservableMap<String, Object> namespace = loader.getNamespace();
        chessmenHbox = (HBox) namespace.get("chessmenHbox");

    }
}
