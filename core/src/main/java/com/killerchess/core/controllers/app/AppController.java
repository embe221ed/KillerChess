package com.killerchess.core.controllers.app;


import javafx.scene.control.Button;

// testing dependencies between view and core module - clicking button in simple app displayed
// in main method of view class should println in your console

public class AppController {
    public Button testButton;

    public void handleTestButtonClicked() {
        System.out.println("Test Button Clicked");
    }
}
