package com.killerchess.core.chessmans;

public class Bishop extends Chessman {

    private static final Integer BISHOP_VALUE = 3;
    private static final Character BISHOP_SYMBOL = 'B';

    public Bishop(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return BISHOP_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return BISHOP_VALUE;
    }
}
