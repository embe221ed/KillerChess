package com.killerchess.view.registration;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.utils.CustomAlert;
import com.killerchess.view.utils.Template;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;

import static com.killerchess.core.config.Constants.HOST;


public class RegistrationController {
    public Button registerButton;
    public Button cancelButton;
    public TextField loginField;
    public TextField passwordField;
    public TextField repeatPasswordField;
    public ImageView firstTemplate;
    public ImageView secondTemplate;
    public ImageView thirdTemplate;

    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();

    private static final String REGISTER_PATH = "/register";

    @FXML
    public void initialize() {
        changeChosenTemplate(firstTemplate);
        addTemplateChoiceHandlers();
    }

    private void addTemplateChoiceHandlers() {
        firstTemplate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.FIRST.toString());
            changeChosenTemplate(firstTemplate);
            event.consume();
        });
        secondTemplate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.SECOND.toString());
            changeChosenTemplate(secondTemplate);
            event.consume();
        });
        thirdTemplate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            localSessionSingleton.setParameter("template", Template.THIRD.toString());
            changeChosenTemplate(thirdTemplate);
            event.consume();
        });
    }

    private void changeChosenTemplate(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        InnerShadow innerShadow = new InnerShadow();
        colorAdjust.setBrightness(-0.3);
        firstTemplate.setEffect(colorAdjust);
        secondTemplate.setEffect(colorAdjust);
        thirdTemplate.setEffect(colorAdjust);
        colorAdjust.setBrightness(0.0);
        imageView.setEffect(innerShadow);
    }

    public void handleRegisterButtonClicked() {
        try {
            String login = loginField.getText();
            String password = passwordField.getText();
            String repeatPassword = repeatPasswordField.getText();

            if (password.equals(repeatPassword)) {
                MultiValueMap<String, String> registrationParametersMap = new LinkedMultiValueMap<>();
                registrationParametersMap.add("username", login);
                registrationParametersMap.add("password", password);

                var responseEntity = localSessionSingleton.exchange(HOST + REGISTER_PATH,
                        HttpMethod.POST, registrationParametersMap, ResponseEntity.class);

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    if (!localSessionSingleton.isCookieSet()) {
                        localSessionSingleton.setCookie(responseEntity);
                    }
                    View.getInstance().changeScene("/loging.fxml");
                }
            } else {
                CustomAlert.showAndWait("Passwords must be the same", Alert.AlertType.INFORMATION);
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                CustomAlert.showAndWait(e.getResponseBodyAsString(), Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleCancelButtonClicked() {
        try {
            System.out.println(localSessionSingleton.getParameter("template"));
            View.getInstance().changeScene("/loging.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
