package com.killerchess.view.roomcreator;

import com.killerchess.core.chessboard.scenarios.GameScenariosEnum;
import com.killerchess.core.session.LocalSessionSingleton;
import com.killerchess.view.logging.LoginController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;
import java.util.UUID;

import static com.killerchess.core.controllers.game.GameController.*;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;
    public VBox gameSchemasRadioButtonsVBox;

    private ToggleGroup toggleGroupForSchemasRadioButtons;

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
    }

    private void createNewGame(String roomName, String roomDatabaseId, String scenarioId) {
        MultiValueMap<String, String> roomCreationParametersMap = new LinkedMultiValueMap<>();
        roomCreationParametersMap.add(GAME_ID_PARAM, roomDatabaseId);
        roomCreationParametersMap.add(GAME_NAME_PARAM, roomName);

        var responseEntity = LocalSessionSingleton.getInstance().exchange(
                LoginController.HOST + NEW_GAME_WITH_NAME_PATH, HttpMethod.POST,
                roomCreationParametersMap,
                ResponseEntity.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            //create first move in game
            Optional<String> scenarioArrangement = GameScenariosEnum.getAllEnumConstants().stream()
                    .filter(gameScenariosEnum -> gameScenariosEnum.getId().toString().equals(scenarioId))
                    .findFirst()
                    .map(GameScenariosEnum::getArrangement);

            if (scenarioArrangement.isPresent()) {

            } else {
                System.out.println("Błąd przy wyborze scenariusza");
            }
        } else {
            System.out.println("Błąd przy tworzeniu gry");
        }
    }

    private void initializeVBoxWithGameScenarios() {
        var vBoxChildren = gameSchemasRadioButtonsVBox.getChildren();
        GameScenariosEnum.getAllEnumConstants()
                .forEach(gameScenario ->
                        vBoxChildren.add(createRadioButtonForScenarioInToggleGroup(gameScenario)));
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
