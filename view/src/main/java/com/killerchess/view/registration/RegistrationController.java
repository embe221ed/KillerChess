package com.killerchess.view.registration;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.logging.LoginController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class RegistrationController {
    public Button registerButton;
    public Button cancelButton;
    public TextField loginField;
    public TextField passwordField;
    public TextField repeatPasswordField;

    private static final String REGISTER_PATH = "/register";

    public void handleRegisterButtonClicked() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();
            if (password.equals(repeatPassword)) {
                MultiValueMap<String, String> registrationParametersMap = new LinkedMultiValueMap<>();
                registrationParametersMap.add("username", login);
                registrationParametersMap.add("password", password);
                RestTemplate restTemplate = new RestTemplate();
                LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
                var requestEntity = localSessionSingleton.getHttpEntity(registrationParametersMap);
                ResponseEntity responseEntity = restTemplate.exchange(LoginController.HOST + REGISTER_PATH,
                        HttpMethod.POST, requestEntity, ResponseEntity.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    if (!localSessionSingleton.isCookieSet()) {
                        localSessionSingleton.setCookie(responseEntity);
                    }
                    View.getInstance().changeScene("/logging.fxml");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Passwords must be the same");
                alert.showAndWait();
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You must fill correctly all fields");
                alert.showAndWait();
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
