package com.killerchess.core.chessmans;

public class EmptyField extends Chessman {

    private static final Integer EMPTY_FIELD__VALUE = 0;
    private static final Character EMPTY_FIELD_SYMBOL = 'X';

    public EmptyField(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return EMPTY_FIELD_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return EMPTY_FIELD__VALUE;
    }
}
