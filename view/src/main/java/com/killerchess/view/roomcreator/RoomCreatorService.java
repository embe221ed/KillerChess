package com.killerchess.view.roomcreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

class RoomCreatorService {

    void createRoom(String roomName, String username, String scenarioId) {
        var json = buildCreateRoomJson(roomName, username, scenarioId);
    }

    private ObjectNode buildCreateRoomJson(String roomName, String username, String scenarioId) {
        var mapper = new ObjectMapper();
        var jsonCreateRoom = mapper.createObjectNode();

        var roomDatabaseId = String.format("%s_%s", roomName, UUID.randomUUID());

        jsonCreateRoom.put("id", roomDatabaseId);
        jsonCreateRoom.put("name", roomName);
        jsonCreateRoom.put("host", username);
        jsonCreateRoom.put("guest", "");
        jsonCreateRoom.put("gameScenarioId", scenarioId);

        return jsonCreateRoom;
    }
}
