package com.killerchess.view.mainpanel;

import com.killerchess.core.dto.GameDTO;
import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.killerchess.core.controllers.app.RankingController.GET_USER_RANKING_PATH;
import static com.killerchess.core.controllers.app.RankingController.RANKING_PATH;
import static com.killerchess.core.controllers.game.GameController.AVAILABLE_GAMES;
import static com.killerchess.core.controllers.user.UserController.GET_LOGIN_PATH;


public class MainPanelController {

    public Text nickName;
    public Text rankingPointsForActualUser;
    public ImageView userAvatar;
    public Button createRoom;
    public ImageView accountImage;
    public TextArea rankingText;
    public ImageView rankingImage;
    public TextArea helpText;
    public ImageView helpImage;
    public ImageView firstPawnChoice;
    public ImageView secondPawnChoice;
    public ImageView thirdPawnChoice;
    public ImageView actualPawnChoice;
    public Button logoutButton;
    public Text actualPawnChoiceText;
    public Text choosePawnText;
    public VBox roomsVBox;


    private String nick;
    private String userPoints;
    private int panelWidth;
    private int panelHeight;
    private boolean selectedAccountTab = true;

    @FXML
    public void initialize() {
        getPanelSize();
        setUserParameters();
        setRanking();
        initializeComponents();
        accountImageListener();
        rankingImageListener();
        hideHelpInfo();
        helpImageListener();
        if (selectedAccountTab) {
            accountListeners();
        }
        initializeRoomsVBox();
    }

    private void initializeRoomsVBox() {
        Text title = new Text(" Pokoje:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        roomsVBox.getChildren().add(title);

        ResponseEntity<List<GameDTO>> roomsResponse = LocalSessionSingleton.getInstance().exchange
                (LoginController.HOST + AVAILABLE_GAMES,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<GameDTO>>() {
                        });
        List<GameDTO> gamesList = roomsResponse.getBody();
        List<TextArea> gamesOptions = new ArrayList<>();

        for (GameDTO gameDTO : gamesList) {
            TextArea gameOption = new TextArea();
            gameOption.setEditable(false);
            gameOption.setText("Pokój: " + gameDTO.getGameName() + "\n"
                    + "host: " + gameDTO.getHost() + "\n");
            gamesOptions.add(gameOption);
        }

        for (TextArea gameOption : gamesOptions) {
            VBox.setMargin(gameOption, new Insets(0, 0, 0, 8));
            roomsVBox.getChildren().add(gameOption);
        }
    }

    private void accountImageListener() {
        accountImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(false);
            helpText.setVisible(false);
            enableAccountFunctions(true);
            event.consume();
        });
    }

    private void accountListeners() {
        firstPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO
            System.out.println("First Pawn Chosen");
            event.consume();
        });

        secondPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO
            System.out.println("Second Pawn Chosen");
            event.consume();
        });

        thirdPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO
            System.out.println("Third Pawn Chosen");
            event.consume();
        });
    }


    private void hideHelpInfo() {
        helpText.setEditable(false);
        helpText.setVisible(false);
    }

    private void helpImageListener() {
        helpImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(false);
            helpText.setVisible(true);
            enableAccountFunctions(false);
            event.consume();
        });
    }

    private void enableAccountFunctions(boolean bool) {
        selectedAccountTab = bool;
        logoutButton.setVisible(bool);
        logoutButton.setDisable(!bool);
        firstPawnChoice.setVisible(bool);
        secondPawnChoice.setVisible(bool);
        thirdPawnChoice.setVisible(bool);
        actualPawnChoice.setVisible(bool);
        choosePawnText.setVisible(bool);
        actualPawnChoiceText.setVisible(bool);
    }

    private void rankingImageListener() {
        rankingImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(true);
            helpText.setVisible(false);
            enableAccountFunctions(false);
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
        File f = new File("images/avatar_");
        if (f.exists() && !f.isDirectory()) {
            Image image = new Image("images/avatar_" + nick + ".jpg", panelWidth / 3, panelHeight / 2, false, false);
            userAvatar.setImage(image);
        }

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


    public void handleLogoutButton() {
        //TODO
        System.out.println("Logout button clicked!");
    }
}
