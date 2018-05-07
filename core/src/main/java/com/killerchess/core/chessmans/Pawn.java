package com.killerchess.core.chessmans;

public class Pawn extends Chessman {

    private static final Integer PAWN_VALUE = 1;
    private static final Character PAWN_SYMBOL = 'P';

    public Pawn(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return PAWN_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return PAWN_VALUE;
    }
}
