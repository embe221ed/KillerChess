package com.killerchess.view.utils;

public enum Templates {
    FIRST("first_pawn"),
    SECOND("second_pawn"),
    THIRD("third_pawn");

    private String filename;

    Templates(String filename) {
        this.filename = filename;
    }

    public String getTemplateFileName() {
        return this.filename;
    }

    public String toString() {
        return getTemplateFileName();
    }
}
