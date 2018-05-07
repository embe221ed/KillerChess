package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Chessman {

    private static final Integer BISHOP_VALUE = 3;
    private static final Character BISHOP_SYMBOL = 'B';

    public Bishop(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return BISHOP_SYMBOL;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
        Integer rookRow = position.getKey();
        Integer rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove;

        boolean upperLeftBlockade = false;
        boolean upperRightBlockade = false;
        boolean bottomLeftBlockade = false;
        boolean bottomRightBlockade = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!upperRightBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol + (row - rookRow));
                upperRightBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, upperRightBlockade);
            }
            if (!bottomRightBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol - (row - rookRow));
                bottomRightBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, bottomRightBlockade);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!upperLeftBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol + (rookRow - row));
                upperLeftBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, upperLeftBlockade);
            }
            if (!bottomLeftBlockade) {
                possibleFieldToMove = new Pair<>(row, rookCol - (rookRow - row));
                bottomLeftBlockade = addEmptyFieldAndCheckBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, bottomLeftBlockade);
            }
        }

        return possibleMoves;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        ChessmanColourEnum colorToCapture = (getColour().equals(ChessmanColourEnum.BLACK)) ?
                ChessmanColourEnum.WHITE : ChessmanColourEnum.BLACK;

        Set<Pair<Integer, Integer>> possibleCaptures = new HashSet<>();
        Integer rookRow = position.getKey();
        Integer rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToCapture;

        boolean upperLeftBlockade = false;
        boolean upperRightBlockade = false;
        boolean bottomLeftBlockade = false;
        boolean bottomRightBlockade = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!upperRightBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol + (row - rookRow));
                upperRightBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperRightBlockade, colorToCapture);
            }
            if (!bottomRightBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol - (row - rookRow));
                bottomRightBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomRightBlockade, colorToCapture);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!upperLeftBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol + (rookRow - row));
                upperLeftBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperLeftBlockade, colorToCapture);
            }
            if (!bottomLeftBlockade) {
                possibleFieldToCapture = new Pair<>(row, rookCol - (rookRow - row));
                bottomLeftBlockade = addNonEmptyFieldAndCheckBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomLeftBlockade, colorToCapture);
            }
        }

        return possibleCaptures;
    }

    @Override
    public Integer getPointsValue() {
        return BISHOP_VALUE;
    }
}
