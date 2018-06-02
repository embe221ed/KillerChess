package com.killerchess.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// simple class which main method displays starting window of app

public class View extends Application {

    private static View instance;
    private Stage stage;

    public View() {
        instance = this;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static View getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.changeScene("/logging.fxml");
    }

    public void changeScene(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        stage.show();
    }
}

