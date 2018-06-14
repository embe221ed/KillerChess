package com.killerchess.view.utils;

public enum Templates {
    FIRST("first_pawn", 1),
    SECOND("second_pawn", 2),
    THIRD("third_pawn", 3);

    private String filename;
    private int chessmanStyleNumber;

    Templates(String filename, int chessmanStyleNumber) {
        this.filename = filename;
        this.chessmanStyleNumber = chessmanStyleNumber;
    }

    public String getTemplateFileName() {
        return this.filename;
    }

    public String toString() {
        return getTemplateFileName();
    }

    public int getChessmanStyleNumber() {
        return chessmanStyleNumber;
    }

    public void setChessmanStyleNumber(int chessmanStyleNumber) {
        this.chessmanStyleNumber = chessmanStyleNumber;
    }
}
