package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.Set;

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
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        return null;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        return null;
    }

    @Override
    public Integer getPointsValue() {
        return KING_VALUE;
    }
}
