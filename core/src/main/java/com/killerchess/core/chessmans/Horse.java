package com.killerchess.core.chessmans;

public class Horse extends Chessman {

    private static final Integer HORSE_VALUE = 3;
    private static final Character HORSE_SYMBOL = 'H';

    public Horse(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return HORSE_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return HORSE_VALUE;
    }
}
