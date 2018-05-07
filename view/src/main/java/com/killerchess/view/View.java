package com.killerchess.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
// simple class which main method displays starting window of app

@SpringBootApplication
@ComponentScan({"com.killerchess.core"})
@EntityScan({"com.killerchess.core"})
public class View extends Application {

    private static View instance;
    private Stage stage;

    public View() {
        instance = this;
    }

    public static void main(String[] args) {
        SpringApplication.run(View.class, args);
//        launch(args);
    }

    public static View getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.changeScene("/logging.fxml");
        SessionFactory testSession = new Configuration().configure().buildSessionFactory();
        Session session = testSession.openSession();
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
