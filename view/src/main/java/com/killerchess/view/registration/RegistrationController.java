package com.killerchess.view.registration;

import com.killerchess.view.View;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrationController {
    public Button registerButton;
    public Button cancelButton;
    public TextField loginField;
    public TextField passwordField;
    public TextField repeatPasswordField;

    public void handleRegisterButtonClicked() {
        try {
            System.out.println("Register button clicked");
            System.out.println("Login: " + loginField.getText());
            if (passwordField.getText().equals(repeatPasswordField.getText())) {
                System.out.println("Passwords are the same");
            } else {
                System.out.println("Passwords are not equal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCancelButtonClicked() {
        try {
            View.getInstance().changeScene("/logging.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
