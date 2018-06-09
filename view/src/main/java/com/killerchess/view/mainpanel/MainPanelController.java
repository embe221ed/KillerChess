package com.killerchess.view.mainpanel;

import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.loging.LoginController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.awt.*;

public class MainPanelController {

    private static final String GET_LOGIN_PATH = "/getLogin";
    private static final String GET_USER_RANKING_PATH = "/getUserRanking";

    public Text nickName;
    public Text rankingPointsForActualUser;
    public ImageView userAvatar;

    private String nick;
    private String userPoints = "2137";
    private int panelWidth;
    private int panelHeight;

    @FXML
    public void initialize() {
        getPanelSize();
        setUserParameters();
        initializeComponents();
    }

    private void setUserParameters() {
        var responseEntity = LocalSessionSingleton.getInstance().exchange(LoginController.HOST + GET_LOGIN_PATH,
                HttpMethod.GET, null, ResponseEntity.class);
        this.nick = responseEntity.getHeaders().getFirst("username");

        responseEntity = LocalSessionSingleton.getInstance().exchange(LoginController.HOST + GET_USER_RANKING_PATH,
                HttpMethod.GET, null, ResponseEntity.class);
        HttpHeaders rankingRegistry = responseEntity.getHeaders();


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


    private void getUserData() {
        //get image
        //get points

        //get nick
    }

    //button handler

    //wylgowanie


}
