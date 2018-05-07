package com.killerchess.view.logging;

import com.killerchess.view.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    public Button loginButton;
    public Button registerButton;
    public TextField loginField;
    public TextField passwordField;

    public void handleLoginButtonClicked() {
        try {
            System.out.println("Login button clicked");
            System.out.println("Login: " + loginField.getText());
            System.out.println("Password: " + passwordField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleRegisterButtonClicked() {
        try {
            System.out.println("Register button clicked");
            View.getInstance().changeScene("/registration.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
