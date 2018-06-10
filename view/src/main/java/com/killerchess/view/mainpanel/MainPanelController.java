package com.killerchess.view.mainpanel;

import com.killerchess.core.dto.GameDTO;
import com.killerchess.core.dto.RankingRegistryDTO;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.core.util.Listener;
import com.killerchess.view.View;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.killerchess.core.controllers.app.RankingController.GET_USER_RANKING_PATH;
import static com.killerchess.core.controllers.app.RankingController.RANKING_PATH;
import static com.killerchess.core.controllers.game.GameController.AVAILABLE_GAMES;
import static com.killerchess.core.controllers.user.UserController.GET_LOGIN_PATH;


public class MainPanelController {

    // TODO delete after correct implementing multithreading
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final String IMAGE_JPEG_MIME_TYPE = "image/jpeg";
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
    public Button changeAvatarButton;
    private final String IMAGES_LOCAL_PATH = "images";
    private String userPoints;
    private final String AVATAR_FILENAME_PREFIX = "/avatar_";
    private final String JPG_FILETYPE_EXTENSION = ".jpg";
    private boolean selectedAccountTab = true;
    public Text usernameText;
    private String username;
    private double panelWidth;
    private double panelHeight;
    private LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();

    @FXML
    public void initialize() {
        getPanelSize();
        setUserParameters();
        setRanking();
        initializeComponents();
        accountImageListener();
        if (selectedAccountTab) {
            accountListeners();
        }
        rankingImageListener();
        hideHelpInfo();
        helpImageListener();
        initializeRoomsVBox();
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
        //TODO MM
        System.out.println("Logout button clicked!");
        // TODO MB delete these lines
        // przykład użycia wątku
        Listener listener = new Listener();
        executorService.submit(listener);
    }

    public void handleAccountAvatarChange() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                String mimeType = Files.probeContentType(file.toPath());
                if (mimeType != null && mimeType.equals(IMAGE_JPEG_MIME_TYPE)) {
                    //TODO MM move images from resources. Allow png format
                    ClassPathResource resource = new ClassPathResource(IMAGES_LOCAL_PATH + AVATAR_FILENAME_PREFIX
                            + username + JPG_FILETYPE_EXTENSION);
                    Files.copy(file.toPath(), Paths.get(ClassLoader.getSystemResource(resource.getPath()).toURI()),
                            StandardCopyOption.REPLACE_EXISTING);
                    Image image = new Image(IMAGES_LOCAL_PATH + AVATAR_FILENAME_PREFIX + username
                            + JPG_FILETYPE_EXTENSION, panelWidth / 3, panelHeight / 2,
                            false, false);
                    userAvatar.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getPanelSize() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        this.panelWidth = visualBounds.getWidth();
        this.panelHeight = visualBounds.getHeight();
    }

    private void setUserParameters() {
        var responseEntity = localSessionSingleton.exchange(
                LoginController.HOST + GET_LOGIN_PATH,
                HttpMethod.GET, null, ResponseEntity.class);
        this.username = responseEntity.getHeaders().getFirst("username");

        ResponseEntity<RankingRegistryDTO> response = localSessionSingleton.exchange(
                LoginController.HOST + GET_USER_RANKING_PATH,
                HttpMethod.GET, null, RankingRegistryDTO.class);
        this.userPoints = String.valueOf(response.getBody().getPoints());
    }

    private void setRanking() {
        ResponseEntity<List<RankingRegistryDTO>> rankingResponse = localSessionSingleton.exchange(
                LoginController.HOST + RANKING_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<RankingRegistryDTO>>() {
                });
        List<RankingRegistryDTO> rankingList = rankingResponse.getBody();

        IntStream.range(0, rankingList.size()).forEach(i -> rankingText.setText(getRankingText(i, rankingList.get(i))));

        rankingText.setEditable(false);
        rankingText.setVisible(false);
    }

    private String getRankingText(int i, RankingRegistryDTO rankingRegistryDTO) {
        return rankingText.getText() + "\n" + (i + 1) + ") " + rankingRegistryDTO.getUsername()
                + " [" + rankingRegistryDTO.getPoints() + "]";
    }

    private void initializeComponents() {
        setNameAndPointsForUser();

        File file;
        try {
            ClassPathResource resource = new ClassPathResource(IMAGES_LOCAL_PATH + AVATAR_FILENAME_PREFIX
                    + username + JPG_FILETYPE_EXTENSION);
            file = resource.getFile();
        } catch (IOException e) {
            file = null;
        }
        if (file != null) {
            Image image = new Image(IMAGES_LOCAL_PATH + AVATAR_FILENAME_PREFIX + username + JPG_FILETYPE_EXTENSION,
                    panelWidth / 3, panelHeight / 2, false, false);
            userAvatar.setImage(image);
        }
    }

    private void setNameAndPointsForUser() {
        usernameText.setText(usernameText.getText() + " " + username);
        rankingPointsForActualUser.setText(rankingPointsForActualUser.getText() + " " + userPoints);
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
            //TODO MM
            System.out.println("First Pawn Chosen");
            event.consume();
        });

        secondPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO MM
            System.out.println("Second Pawn Chosen");
            event.consume();
        });

        thirdPawnChoice.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //TODO MM
            System.out.println("Third Pawn Chosen");
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
        changeAvatarButton.setVisible(bool);
        changeAvatarButton.setDisable(!bool);
    }

    private void rankingImageListener() {
        rankingImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            rankingText.setVisible(true);
            helpText.setVisible(false);
            enableAccountFunctions(false);
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

    private void initializeRoomsVBox() {
        Text title = new Text(" Rooms:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        getRoomsVBoxChildren().add(title);

        ResponseEntity<List<GameDTO>> roomsResponse = localSessionSingleton.exchange(
                LoginController.HOST + AVAILABLE_GAMES, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GameDTO>>() {
                });

        List<GameDTO> gamesList = roomsResponse.getBody();
        List<TextArea> gamesOptions = new ArrayList<>();

        for (GameDTO gameDTO : gamesList) {
            TextArea gameOption = new TextArea();
            gameOption.setEditable(false);
            gameOption.setText("Room: " + gameDTO.getGameName() + "\n"
                    + "host: " + gameDTO.getHost() + "\n");
            gamesOptions.add(gameOption);
        }

        for (TextArea gameOption : gamesOptions) {
            VBox.setMargin(gameOption, new Insets(0, 0, 0, 8));
            getRoomsVBoxChildren().add(gameOption);
        }

        roomsVBox.setOnMouseClicked((e) -> roomsVBox.requestFocus());

        //TODO MM make listeners for TextAreas. One click brings information about room, two starts game.
    }

    private ObservableList<Node> getRoomsVBoxChildren() {
        return roomsVBox.getChildren();
    }

}