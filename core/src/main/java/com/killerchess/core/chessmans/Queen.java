package com.killerchess.core.chessmans;

public class Queen extends Chessman {

    private static final Character QUEEN_SYMBOL = 'Q';

    public Queen(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return QUEEN_SYMBOL;
    }
}
