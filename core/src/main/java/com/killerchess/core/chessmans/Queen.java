package com.killerchess.core.chessmans;

public class Queen extends Chessman {

    private static final Integer QUEEN_VALUE = 9;
    private static final Character QUEEN_SYMBOL = 'Q';

    public Queen(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return QUEEN_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return QUEEN_VALUE;
    }
}
