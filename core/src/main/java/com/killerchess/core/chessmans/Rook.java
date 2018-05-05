package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Chessman {

    private static final Character ROOK_SYMBOL = 'R';

    public Rook(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return ROOK_SYMBOL;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> possibleCaptures = new HashSet<>();
        Integer rookRow = position.getKey();
        Integer rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToCapture;
        for (int row = rookRow; row < 8; row++) {
            possibleFieldToCapture = new Pair<>(row, rookCol);
            if (isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                possibleCaptures.add(possibleFieldToCapture);
            } else {
                break;
            }
        }
        for (int row = rookRow; row >= 0; row--) {
            possibleFieldToCapture = new Pair<>(row, rookCol);
            if (isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                possibleCaptures.add(possibleFieldToCapture);
            } else {
                break;
            }
        }
        for (int col = rookCol; col < 8; col++) {
            possibleFieldToCapture = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                possibleCaptures.add(possibleFieldToCapture);
            } else {
                break;
            }
        }
        for (int col = rookCol; col >= 0; col--) {
            possibleFieldToCapture = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToCapture)) {
                possibleCaptures.add(possibleFieldToCapture);
            } else {
                break;
            }
        }

        return possibleCaptures;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        return null;
    }
}
