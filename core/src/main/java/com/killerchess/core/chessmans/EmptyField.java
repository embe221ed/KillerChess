package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.Set;

public class EmptyField extends Chessman {

    private static final Integer EMPTY_FIELD__VALUE = 0;
    private static final Character EMPTY_FIELD_SYMBOL = 'X';

    public EmptyField(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return EMPTY_FIELD_SYMBOL;
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
        return EMPTY_FIELD__VALUE;
    }
}
