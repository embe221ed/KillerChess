package com.killerchess.core.chessmans;

public enum ChessmanColourEnum {
    BLACK, WHITE, EMPTY;

    public Character getSymbol() {
        switch (this) {
            case BLACK:
                return 'B';
            case WHITE:
                return 'W';
            default:
                return 'X';
        }
    }
}
