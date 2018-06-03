package com.killerchess.view.roomcreator;

import com.killerchess.view.session.LocalSessionSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;
    public VBox gameSchemasRadioButtonsVBox;

    @FXML
    public void initialize() {
        initializeVBoxWithGameSchemas();
    }

    public void handleCreateRoomButtonClick() {
        var roomName = roomNameTextField.getCharacters().toString();
        var username = LocalSessionSingleton.getInstance().getParameter("username");
    }

    private void initializeVBoxWithGameSchemas() {
        RadioButton radioButton = new RadioButton("aaaa");
        gameSchemasRadioButtonsVBox.getChildren().addAll(radioButton);
    }


}
