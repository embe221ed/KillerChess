package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.Set;

public class Queen extends Chessman {

    private static final Integer QUEEN_VALUE = 9;
    private static final Character QUEEN_SYMBOL = 'Q';

    public Queen(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return QUEEN_SYMBOL;
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
        return QUEEN_VALUE;
    }
}
