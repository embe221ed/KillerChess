package com.killerchess.view.roomcreator;

import com.killerchess.core.chessboard.scenarios.GameScenariosEnum;
import com.killerchess.core.session.LocalSessionSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;
    public VBox gameSchemasRadioButtonsVBox;

    private RoomCreatorService roomCreatorService;
    private ToggleGroup toggleGroupForSchemasRadioButtons;

    @FXML
    public void initialize() {
        initializeControllerFields();
        initializeComponents();
    }

    private void initializeControllerFields() {
        roomCreatorService = new RoomCreatorService();
        toggleGroupForSchemasRadioButtons = new ToggleGroup();
    }

    private void initializeComponents() {
        initializeVBoxWithGameScenarios();
    }

    public void handleCreateRoomButtonClick() {
        var roomName = roomNameTextField.getCharacters().toString();
        var username = LocalSessionSingleton.getInstance().getParameter("username");
        var selectedScenario = (RadioButton) toggleGroupForSchemasRadioButtons.getSelectedToggle();
        var scenarioId = selectedScenario.getId();
        roomCreatorService.createRoom(roomName, username, scenarioId);
    }

    private void initializeVBoxWithGameScenarios() {
        var vBoxChildren = gameSchemasRadioButtonsVBox.getChildren();
        GameScenariosEnum.getAllEnumConstants()
                .forEach(gameScenario ->
                        vBoxChildren.add(createRadioButtonForScenarioInToggleGroup(gameScenario)));
    }

    private RadioButton createRadioButtonForScenarioInToggleGroup(GameScenariosEnum gameScenariosEnum) {
        var radioButtonForSchema = new RadioButton(gameScenariosEnum.getDescription());
        radioButtonForSchema.setToggleGroup(toggleGroupForSchemasRadioButtons);
        radioButtonForSchema.setId(gameScenariosEnum.getId().toString());
        return radioButtonForSchema;
    }


}
