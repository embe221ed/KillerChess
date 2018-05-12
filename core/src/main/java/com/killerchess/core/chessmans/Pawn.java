package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Chessman {

    private static final Integer PAWN_VALUE = 1;
    private static final Character PAWN_SYMBOL = 'P';

    public Pawn(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return PAWN_SYMBOL;
    }

    @Override
    public Integer getPointsValue() {
        return PAWN_VALUE;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var pawnsRow = position.getKey();
        var pawnsCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove = null;

        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            possibleFieldToMove = new Pair<>(pawnsRow - 1, pawnsCol);
        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            possibleFieldToMove = new Pair<>(pawnsRow + 1, pawnsCol);
        }

        if (isFieldWithinBoard(possibleFieldToMove)) {
            if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            } else {
                return possibleMoves;
            }
        } else {
            return possibleMoves;
        }

        if (getColour().equals(ChessmanColourEnum.BLACK) && pawnsRow.equals(6)) {
            possibleFieldToMove = new Pair<>(pawnsRow - 2, pawnsCol);
            if (isFieldWithinBoard(possibleFieldToMove) && isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
            }

        } else if (getColour().equals(ChessmanColourEnum.WHITE) && pawnsRow.equals(1)) {
            possibleFieldToMove = new Pair<>(pawnsRow + 2, pawnsCol);
            if (isFieldWithinBoard(possibleFieldToMove) && isFieldEmpty(chessBoard, possibleFieldToMove)) {
                possibleMoves.add(possibleFieldToMove);
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
        var pawnsRow = position.getKey();
        var pawnsCol = position.getValue();

        var searchRow = (colorToCapture.equals(ChessmanColourEnum.WHITE)) ? (pawnsRow - 1) : (pawnsRow + 1);

        var possibleFieldToCapture = new Pair<>(searchRow, pawnsCol - 1);
        addFieldIfIsWithinBoardAndMatchesColour(chessBoard, possibleCaptures, possibleFieldToCapture, colorToCapture);

        possibleFieldToCapture = new Pair<>(searchRow, pawnsCol + 1);
        addFieldIfIsWithinBoardAndMatchesColour(chessBoard, possibleCaptures, possibleFieldToCapture, colorToCapture);


        return possibleCaptures;
    }

    boolean isPromotionAvailable(Pair<Integer, Integer> position) {
        var pawnsRow = position.getKey();
        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            return pawnsRow.equals(0);
        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            return pawnsRow.equals(7);
        }
        return false;
    }
}
