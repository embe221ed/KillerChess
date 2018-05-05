package com.killerchess.core.chessmans;

public class Rook extends Chessman {

    private static final Character ROOK_SYMBOL = 'R';

    public Rook(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return ROOK_SYMBOL;
    }
}
