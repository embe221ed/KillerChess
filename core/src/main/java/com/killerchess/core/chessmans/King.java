package com.killerchess.core.chessmans;

public class King extends Chessman {

    private static final Integer KING_VALUE = 7;
    private static final Character KING_SYMBOL = 'K';

    public King(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return KING_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return KING_VALUE;
    }
}
