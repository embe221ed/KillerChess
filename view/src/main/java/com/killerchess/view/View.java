package com.killerchess.view;

import com.killerchess.core.session.LocalSessionSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {

    private static View instance;
    private Stage stage;
    private static String initFxmlFilePath;

    public View() {
        instance = this;
    }

    public static View getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        setInitFxmlFilePath("/loging.fxml");
        launch(args);
    }

    public static void setInitFxmlFilePath(String initFxmlFilePath) {
        View.initFxmlFilePath = initFxmlFilePath;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        setPrimaryStageProperties(primaryStage);
        this.changeScene(initFxmlFilePath);
    }

    private void setPrimaryStageProperties(Stage primaryStage) {
        primaryStage.setResizable(true);
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

    @Override
    public void stop() {
        LocalSessionSingleton.getInstance().saveToConfigFile();
    }

    public Stage getStage() {
        return this.stage;
    }
}

