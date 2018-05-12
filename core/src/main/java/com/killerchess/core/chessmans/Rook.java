package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Chessman {

    private static final Integer ROOK_VALUE = 5;
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
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove;

        for (int row = rookRow + 1; row < 8; row++) {
            possibleFieldToMove = new Pair<>(row, rookCol);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            possibleFieldToMove = new Pair<>(row, rookCol);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }
        for (int col = rookCol + 1; col < 8; col++) {
            possibleFieldToMove = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }
        for (int col = rookCol - 1; col >= 0; col--) {
            possibleFieldToMove = new Pair<>(rookRow, col);
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                break;
            }
        }

        return possibleMoves;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var colorToCapture = (getColour().equals(ChessmanColourEnum.BLACK)) ?
                ChessmanColourEnum.WHITE : ChessmanColourEnum.BLACK;

        var possibleCaptures = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        for (int row = rookRow + 1; row < 8; row++) {
            if (addNonEmptyChessmanFromGivenColorToFieldSet(chessBoard, colorToCapture, possibleCaptures, rookCol, row))
                break;
        }
        for (int row = rookRow - 1; row >= 0; row--) {
            if (addNonEmptyChessmanFromGivenColorToFieldSet(chessBoard, colorToCapture, possibleCaptures, rookCol, row))
                break;
        }
        for (int col = rookCol + 1; col < 8; col++) {
            if (addNonEmptyChessmanFromGivenColorToFieldSet(chessBoard, colorToCapture, possibleCaptures, col, rookRow))
                break;
        }
        for (int col = rookCol - 1; col >= 0; col--) {
            if (addNonEmptyChessmanFromGivenColorToFieldSet(chessBoard, colorToCapture, possibleCaptures, col, rookRow))
                break;
        }

        return possibleCaptures;
    }

    @Override
    public Integer getPointsValue() {
        return ROOK_VALUE;
    }
}
