package com.killerchess.view.roomcreator;

import com.killerchess.core.chessboard.scenarios.GameScenariosEnum;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.View;
import com.killerchess.view.game.GameBoard;
import com.killerchess.view.loging.LoginController;
import com.killerchess.view.utils.CustomAlert;
import com.killerchess.view.utils.SoundPlayer;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

import static com.killerchess.core.controllers.game.GameController.*;
import static com.killerchess.view.game.GameBoard.SLEEP_TIME;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;
    public VBox gameSchemasRadioButtonsVBox;

    private ToggleGroup toggleGroupForSchemasRadioButtons;
    private Service hostJoinedListenerService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() {
                    LocalSessionSingleton localSessionSingleton = LocalSessionSingleton.getInstance();
                    ResponseEntity<Boolean> responseEntity;
                    UriComponentsBuilder builder;
                    try {
                        do {
                            Thread.sleep(SLEEP_TIME);
                            builder = UriComponentsBuilder.fromHttpUrl(LoginController.HOST + CHECK_GUEST_PATH);
                            responseEntity = localSessionSingleton
                                    .exchange(builder.toUriString(), HttpMethod.GET, null, Boolean.class);

                        } while (!responseEntity.getBody());
                        new Thread(SoundPlayer::playOnGameStart).start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
    };

    @FXML
    public void initialize() {
        initializeControllerFields();
        initializeComponents();
    }

    private void initializeControllerFields() {
        toggleGroupForSchemasRadioButtons = new ToggleGroup();
    }

    private void initializeComponents() {
        initializeVBoxWithGameScenarios();
    }

    public void handleCreateRoomButtonClick() {
        var roomName = roomNameTextField.getCharacters().toString();
        var roomDatabaseId = String.format("%s_%s", roomName, UUID.randomUUID());
        var selectedScenario = (RadioButton) toggleGroupForSchemasRadioButtons.getSelectedToggle();
        var scenarioId = selectedScenario.getId();
        createNewGame(roomName, roomDatabaseId, scenarioId);
        try {
            new GameBoard().start(View.getInstance().getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNewGame(String roomName, String roomDatabaseId, String scenarioId) {
        MultiValueMap<String, String> roomCreationParametersMap = new LinkedMultiValueMap<>();
        roomCreationParametersMap.add(GAME_ID_PARAM, roomDatabaseId);
        roomCreationParametersMap.add(GAME_NAME_PARAM, roomName);

        var session = LocalSessionSingleton.getInstance();
        var responseEntity = session.exchange(
                LoginController.HOST + NEW_GAME_PATH, HttpMethod.POST,
                roomCreationParametersMap,
                ResponseEntity.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            createInitialMoveInGame(roomDatabaseId, scenarioId);
        } else {
            CustomAlert.showAndWait("Błąd przy tworzeniu gry", Alert.AlertType.ERROR);
        }
    }

    private void createInitialMoveInGame(String roomDatabaseId, String scenarioId) {
        Optional<String> scenarioArrangement = GameScenariosEnum.getAllEnumConstants().stream()
                .filter(gameScenariosEnum -> gameScenariosEnum.getId().toString().equals(scenarioId))
                .findFirst()
                .map(GameScenariosEnum::getArrangement);

        if (scenarioArrangement.isPresent()) {
            MultiValueMap<String, String> gameStateCreationParametersMap = new LinkedMultiValueMap<>();
            gameStateCreationParametersMap.add(STATE_PARAM, scenarioArrangement.get());
            gameStateCreationParametersMap.add(GAME_ID_PARAM, roomDatabaseId);

            var session = LocalSessionSingleton.getInstance();
            var responseEntity = session.exchange(LoginController.HOST + FIRST_GAME_STATE_PATH,
                    HttpMethod.POST, gameStateCreationParametersMap, Integer.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                session.setParameter(GAME_STATE_NUMBER_PARAM, responseEntity.getBody().toString());
                changeSceneToGameBoard();
            } else {
                CustomAlert.showAndWait("Couldn't created first move.", Alert.AlertType.ERROR);
            }
        } else {
            CustomAlert.showAndWait("Error occurred while choosing scenario.", Alert.AlertType.ERROR);
        }
    }

    private void changeSceneToGameBoard() {
        Stage stage = View.getInstance().getStage();
        stage.setResizable(true);
        GameBoard gameBoard = GameBoard.getInstance();
        try {
            gameBoard.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForHost();
    }

    private void waitForHost() {
        hostJoinedListenerService.start();
    }

    private void initializeVBoxWithGameScenarios() {
        var vBoxChildren = gameSchemasRadioButtonsVBox.getChildren();
        GameScenariosEnum.getAllEnumConstants()
                .forEach(gameScenario -> vBoxChildren.add(createRadioButtonForScenarioInToggleGroup(gameScenario)));
        fireFirstButton(vBoxChildren);
    }

    private RadioButton createRadioButtonForScenarioInToggleGroup(GameScenariosEnum gameScenariosEnum) {
        var radioButtonForSchema = new RadioButton(gameScenariosEnum.getDescription());
        radioButtonForSchema.setToggleGroup(toggleGroupForSchemasRadioButtons);
        radioButtonForSchema.setId(gameScenariosEnum.getId().toString());
        return radioButtonForSchema;
    }

    private void fireFirstButton(ObservableList<Node> vBoxChildren) {
        RadioButton firstButton = (RadioButton) vBoxChildren.get(0);
        firstButton.fire();
    }


}
