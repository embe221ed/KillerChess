package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.Set;

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
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        return null;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        return null;
    }

    @Override
    public Integer getPointsValue() {
        return HORSE_VALUE;
    }
}
