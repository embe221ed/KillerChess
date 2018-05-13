package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
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
    public Integer getPointsValue() {
        return KING_VALUE;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove;

        for (int row = rookRow - 1; row <= rookRow + 1; row++) {
            for (int col = rookCol - 1; col <= rookCol + 1; col++) {
                if (!(row == rookRow && col == rookCol)) {
                    possibleFieldToMove = new Pair<>(row, col);
                    if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
                        possibleMoves.add(possibleFieldToMove);
                }
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
        var rookRow = position.getKey();
        var rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToCapture;

        for (int row = rookRow - 1; row <= rookRow + 1; row++) {
            for (int col = rookCol - 1; col <= rookCol + 1; col++) {
                if (!(row == rookRow && col == rookCol)) {
                    possibleFieldToCapture = new Pair<>(row, col);
                    if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                            && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
                        possibleCaptures.add(possibleFieldToCapture);
                }
            }
        }

        return possibleCaptures;
    }
}
