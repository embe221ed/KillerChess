package com.killerchess.view.mainpanel;

import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.awt.*;
import java.util.List;

import static com.killerchess.core.controllers.app.RankingController.GET_USER_RANKING_PATH;
import static com.killerchess.core.controllers.app.RankingController.RANKING_PATH;
import static com.killerchess.core.controllers.user.UserController.GET_LOGIN_PATH;


public class MainPanelController {

    public Text nickName;
    public Text rankingPointsForActualUser;
    public ImageView userAvatar;
    public Button createRoom;
    public TextArea rankingText;
    public ImageView rankingImage;
    public TextArea helpText;
    public ImageView helpImage;


    private String nick;
    private String userPoints;
    private int panelWidth;
    private int panelHeight;

    @FXML
    public void initialize() {
        getPanelSize();
        setUserParameters();
        setRanking();
        initializeComponents();
        rankingImageListener();
        hideHelpInfo();
        helpImageListener();


    }

    private void hideHelpInfo() {
        helpText.setEditable(false);
        helpText.setVisible(false);
    }

    private void helpImageListener() {
        helpImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(false);
            helpText.setVisible(true);
            event.consume();
        });
    }

    private void rankingImageListener() {
        rankingImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(true);
            helpText.setVisible(false);
            event.consume();
        });
    }


    private void setRanking() {
        ResponseEntity<List<RankingRegistryDTO>> rankingResponse = LocalSessionSingleton.getInstance().exchange
                (LoginController.HOST + RANKING_PATH,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<RankingRegistryDTO>>() {
                        });
        List<RankingRegistryDTO> rankingList = rankingResponse.getBody();

        for (int i = 0; i < rankingList.size(); i++) {
            rankingText.setText(rankingText.getText() + "\n" + (i + 1) + ") " + rankingList.get(i).getUsername() + " " +
                    "[" + rankingList.get(i).getPoints() + "]");
        }
        rankingText.setEditable(false);
        rankingText.setVisible(false);
    }

    private void setUserParameters() {
        var responseEntity = LocalSessionSingleton.getInstance().exchange(LoginController.HOST + GET_LOGIN_PATH,
                HttpMethod.GET, null, ResponseEntity.class);
        this.nick = responseEntity.getHeaders().getFirst("username");

        ResponseEntity<RankingRegistryDTO> response = LocalSessionSingleton.getInstance().exchange(LoginController
                        .HOST + GET_USER_RANKING_PATH,
                HttpMethod.GET, null, RankingRegistryDTO.class);
        this.userPoints = String.valueOf(response.getBody().getPoints());
    }

    private void getPanelSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.panelWidth = gd.getDisplayMode().getWidth();
        this.panelHeight = gd.getDisplayMode().getHeight();
    }


    private void initializeComponents() {
        nickName.setText(nickName.getText() + " " + nick);
        rankingPointsForActualUser.setText(rankingPointsForActualUser.getText() + " " + userPoints);
        Image image = new Image("images/avatar_" + nick + ".jpg", panelWidth / 3, panelHeight / 2, false, false);
        userAvatar.setImage(image);
    }

    public void handleNewRoomButtonClicked() {
        try {
            View.getInstance().changeScene("/room_creator.fxml");
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError()) {
                CustomAlert.showAndWait(e.getResponseBodyAsString(), Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
