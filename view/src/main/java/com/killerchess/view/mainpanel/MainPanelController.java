package com.killerchess.view.mainpanel;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.*;

public class MainPanelController {


    public static final String HOST = "http://localhost:8080";
    public Text nickName;
    public Text rankingPointsForActualUser;
    public ImageView userAvatar;

    private String nick = "admin";
    private int panelWidth;
    private int panelHeight;

    @FXML
    public void initialize() {
        getPanelSize();
        initializeComponents();
    }

    private void getPanelSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.panelWidth = gd.getDisplayMode().getWidth();
        this.panelHeight = gd.getDisplayMode().getHeight();
    }


    private void initializeComponents() {
        nickName.setText(nickName.getText() + " " + nick);
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
