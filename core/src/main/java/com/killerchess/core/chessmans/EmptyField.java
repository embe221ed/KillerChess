package com.killerchess.core.chessmans;

public class EmptyField extends Chessman {

    private static final Character EMPTY_FIELD_SYMBOL = 'X';

    public EmptyField(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return EMPTY_FIELD_SYMBOL;
    }
}
