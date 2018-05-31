package com.killerchess.view.logging;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class LoginController {
    public Button loginButton;
    public Button registerButton;
    public TextField loginField;
    public TextField passwordField;

    private static final String LOGIN_URL = "http://localhost:8080/login";

    public void handleLoginButtonClicked() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();
            ResponseEntity responseEntity;
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", login);
            map.add("password", password);
            RestTemplate restTemplate = new RestTemplate();
            LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
            var requestEntity = localSessionSingleton.getHttpEntity(map);
            responseEntity = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, requestEntity, ResponseEntity.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                if (!localSessionSingleton.isSetCookie()) {
                    localSessionSingleton.setCookie(responseEntity);
                }
                // Getting information from REST server (in example the information is username)
                requestEntity = localSessionSingleton.getHttpEntity(map);
                responseEntity = restTemplate.exchange(LOGIN_URL, HttpMethod.GET, requestEntity, ResponseEntity.class);
                localSessionSingleton.addParameter("username", responseEntity.getHeaders().getFirst("username"));
                System.out.println(localSessionSingleton.getParameter("username"));

                View.getInstance().changeScene("/main_screen.fxml");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
            }
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
