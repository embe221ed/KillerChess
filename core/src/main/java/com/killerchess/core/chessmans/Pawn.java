package com.killerchess.core.chessmans;

public class Pawn extends Chessman {

    private static final Character PAWN_SYMBOL = 'P';

    public Pawn(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return PAWN_SYMBOL;
    }
}
