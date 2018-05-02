package com.killerchess.core.chessmans;

public class Horse extends Chessman {

    private static final Character HORSE_SYMBOL = 'H';

    public Horse(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return HORSE_SYMBOL;
    }
}
