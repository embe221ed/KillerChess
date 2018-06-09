package com.killerchess.view.loging;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.utils.CustomAlert;
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

    public static final String HOST = "http://localhost:8080";
    private static final String LOGIN_PATH = "/login";
    private static final String GET_LOGIN_PATH = "/getLogin";

    public void handleLoginButtonClicked() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();
            MultiValueMap<String, String> loginParametersMap = new LinkedMultiValueMap<>();
            loginParametersMap.add("username", login);
            loginParametersMap.add("password", password);
            RestTemplate restTemplate = new RestTemplate();
            LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
            var requestEntity = localSessionSingleton.getHttpEntity(loginParametersMap);
            ResponseEntity responseEntity = restTemplate.exchange(HOST + LOGIN_PATH, HttpMethod.POST, requestEntity, ResponseEntity.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                if (!localSessionSingleton.isCookieSet()) {
                    localSessionSingleton.setCookie(responseEntity);
                }
                // TODO delete
                // Getting information from REST server (in example the information is username)
                // Getting HttpEntity which is later send to server
                requestEntity = localSessionSingleton.getHttpEntity(loginParametersMap);
                // exchange data with server
                responseEntity = restTemplate.exchange(HOST + GET_LOGIN_PATH, HttpMethod.GET, requestEntity, ResponseEntity.class);
                // add parameter to local session to have global access to data
                localSessionSingleton.addParameter("username", responseEntity.getHeaders().getFirst("username"));
                System.out.println(localSessionSingleton.getParameter("username"));

                View.getInstance().changeScene("/main_screen.fxml");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                CustomAlert.showAndWait(e.getResponseBodyAsString(), Alert.AlertType.ERROR);
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
