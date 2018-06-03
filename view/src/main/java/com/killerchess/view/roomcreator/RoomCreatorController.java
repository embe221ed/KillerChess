package com.killerchess.view.roomcreator;

import com.killerchess.core.session.LocalSessionSingleton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RoomCreatorController {

    public Button createRoomButton;
    public TextField roomNameTextField;

    public void handleCreateRoomButtonClick() {
        var roomName = roomNameTextField.getCharacters().toString();
        var username = LocalSessionSingleton.getInstance().getParameter("username");

    }


}
