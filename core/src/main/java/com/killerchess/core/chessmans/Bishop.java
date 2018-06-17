package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import org.springframework.data.util.Pair;

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
    public Integer getPointsValue() {
        return BISHOP_VALUE;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getFirst();
        var rookCol = position.getSecond();

        Pair<Integer, Integer> possibleFieldToMove;

        var isUpperLeftBlockadeSet = false;
        var isUpperRightBlockadeSet = false;
        var isBottomLeftBlockadeSet = false;
        var isBottomRightBlockadeSet = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!isUpperRightBlockadeSet) {
                possibleFieldToMove = Pair.of(row, rookCol + (row - rookRow));
                isUpperRightBlockadeSet = addEmptyFieldAndUpdateBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, isUpperRightBlockadeSet);
            }
            if (!isBottomRightBlockadeSet) {
                possibleFieldToMove = Pair.of(row, rookCol - (row - rookRow));
                isBottomRightBlockadeSet = addEmptyFieldAndUpdateBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, isBottomRightBlockadeSet);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!isUpperLeftBlockadeSet) {
                possibleFieldToMove = Pair.of(row, rookCol + (rookRow - row));
                isUpperLeftBlockadeSet = addEmptyFieldAndUpdateBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, isUpperLeftBlockadeSet);
            }
            if (!isBottomLeftBlockadeSet) {
                possibleFieldToMove = Pair.of(row, rookCol - (rookRow - row));
                isBottomLeftBlockadeSet = addEmptyFieldAndUpdateBlockade(chessBoard, possibleMoves,
                        possibleFieldToMove, isBottomLeftBlockadeSet);
            }
        }

        return possibleMoves;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var colorToCapture = getColour().equals(ChessmanColourEnum.BLACK)
                ? ChessmanColourEnum.WHITE
                : ChessmanColourEnum.BLACK;

        var possibleCaptures = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getFirst();
        var rookCol = position.getSecond();

        Pair<Integer, Integer> possibleFieldToCapture;

        var upperLeftBlockade = false;
        var upperRightBlockade = false;
        var bottomLeftBlockade = false;
        var bottomRightBlockade = false;


        for (int row = rookRow + 1; row < 8; row++) {
            if (!upperRightBlockade) {
                possibleFieldToCapture = Pair.of(row, rookCol + (row - rookRow));
                upperRightBlockade = addNonEmptyFieldAndUpdateBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperRightBlockade, colorToCapture);
            }
            if (!bottomRightBlockade) {
                possibleFieldToCapture = Pair.of(row, rookCol - (row - rookRow));
                bottomRightBlockade = addNonEmptyFieldAndUpdateBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomRightBlockade, colorToCapture);
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (!upperLeftBlockade) {
                possibleFieldToCapture = Pair.of(row, rookCol + (rookRow - row));
                upperLeftBlockade = addNonEmptyFieldAndUpdateBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, upperLeftBlockade, colorToCapture);
            }
            if (!bottomLeftBlockade) {
                possibleFieldToCapture = Pair.of(row, rookCol - (rookRow - row));
                bottomLeftBlockade = addNonEmptyFieldAndUpdateBlockade(chessBoard, possibleCaptures,
                        possibleFieldToCapture, bottomLeftBlockade, colorToCapture);
            }
        }

        return possibleCaptures;
    }
}
