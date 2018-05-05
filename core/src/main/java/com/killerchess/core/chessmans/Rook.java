package com.killerchess.core.chessmans;

public class Rook extends Chessman {

    private static final Integer ROOK_VALUE = 5;
    private static final Character ROOK_SYMBOL = 'R';

    public Rook(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return ROOK_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return ROOK_VALUE;
    }
}
