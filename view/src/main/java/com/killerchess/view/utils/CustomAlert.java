package com.killerchess.view.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomAlert {

    public static void showAndWait(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
