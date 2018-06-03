package com.killerchess.core.chessboard.scenarios;

import java.util.Arrays;
import java.util.List;

public enum GameScenariosEnum {
    CLASSICAL(0, "Classical arrangement", "{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}"
    ),
    NO_PAWNS(1, "No pawns", "{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}"
    ),
    NO_QUEENS(2, "No Queens", "{\"1\":[\"RW\",\"HW\",\"BW\",\"XX\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"XX\",\"KB\",\"BB\",\"HB\",\"RB\"]}"
    ),
    MIXED(3, "Chessmen in first and last row mixed - extreme", "{\"1\":[\"BW\",\"HW\",\"BW\",\"HW\",\"KW\",\"RW\",\"QW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"BB\",\"HB\",\"BB\",\"HB\",\"KB\",\"RB\",\"QB\",\"RB\"]}"
    ),
    VERTICAL(4, "Chessmen positioned vertically, no pawns - extreme", "{\"1\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"RB\"],\"2\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\"],\"3\":[\"BW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"BB\"],\"4\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"KB\"],\"5\":[\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QB\"],\"6\":[\"BW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"BB\"],\"7\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\"],\"8\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"RB\"]}"
    );

    Integer id;
    String description;
    String arrangement;

    GameScenariosEnum(Integer id, String description, String arrangement) {
        this.id = id;
        this.description = description;
        this.arrangement = arrangement;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getArrangement() {
        return arrangement;
    }

    public List<GameScenariosEnum> getAllEnumConstants() {
        return Arrays.asList(GameScenariosEnum.class.getEnumConstants());
    }
}
