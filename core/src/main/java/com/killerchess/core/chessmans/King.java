package com.killerchess.core.chessmans;

public class King extends Chessman {

    private static final Character KING_SYMBOL = 'K';

    public King(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return KING_SYMBOL;
    }
}
