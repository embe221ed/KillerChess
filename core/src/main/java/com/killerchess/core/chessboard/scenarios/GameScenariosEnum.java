package com.killerchess.core.chessboard.scenarios;

public enum GameScenariosEnum {
    CLASSICAL("{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}",
            "Classical arrangement"),
    NO_PAWNS("{\"1\":[\"RW\",\"HW\",\"BW\",\"QW\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"8\":[\"RB\",\"HB\",\"BB\",\"QB\",\"KB\",\"BB\",\"HB\",\"RB\"]}",
            "No pawns"),
    NO_QUEENS("{\"1\":[\"RW\",\"HW\",\"BW\",\"XX\",\"KW\",\"BW\",\"HW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"RB\",\"HB\",\"BB\",\"XX\",\"KB\",\"BB\",\"HB\",\"RB\"]}",
            "No Queens"),
    MIXED("{\"1\":[\"BW\",\"HW\",\"BW\",\"HW\",\"KW\",\"RW\",\"QW\",\"RW\"],\"2\":[\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\",\"PW\"],\"3\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"4\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"5\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"6\":[\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\"],\"7\":[\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\",\"PB\"],\"8\":[\"BB\",\"HB\",\"BB\",\"HB\",\"KB\",\"RB\",\"QB\",\"RB\"]}",
            "Chessmen in first and last row mixed - extreme"),
    VERTICAL("{\"1\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"RB\"],\"2\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\"],\"3\":[\"BW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"BB\"],\"4\":[\"QW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"KB\"],\"5\":[\"KW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"QB\"],\"6\":[\"BW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"BB\"],\"7\":[\"HW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"HB\"],\"8\":[\"RW\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"XX\",\"RB\"]}",
            "Chessmen positioned vertically, no pawns - extreme");

    String arrangement;
    String description;

    GameScenariosEnum(String arrangement, String description) {
        this.arrangement = arrangement;
        this.description = description;
    }

    public String getArrangement() {
        return arrangement;
    }

    public String getDescription() {
        return description;
    }
}
