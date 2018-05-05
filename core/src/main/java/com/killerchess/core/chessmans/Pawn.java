package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Chessman {

    private static final Character PAWN_SYMBOL = 'P';

    public Pawn(ChessmanColourEnum colour) {
        super(colour);
    }

    @Override
    public Character getSymbol() {
        return PAWN_SYMBOL;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
        Integer pawnsRow = position.getKey();
        Integer pawnsCol = position.getValue();

        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            Pair<Integer, Integer> possibleFieldToMove =
                    new Pair<>(Integer.valueOf(pawnsRow - 1), Integer.valueOf(pawnsCol));
            if (isFieldWithinBoard(possibleFieldToMove)) {
                if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                    possibleMoves.add(possibleFieldToMove);
                } else {
                    return possibleMoves;
                }
            } else {
                return possibleMoves;
            }

            if (pawnsRow.equals(6)) {
                Pair<Integer, Integer> nextPossibleFieldToMove =
                        new Pair<>(Integer.valueOf(pawnsRow - 2), Integer.valueOf(pawnsCol));
                if (isFieldWithinBoard(nextPossibleFieldToMove) && isFieldEmpty(chessBoard, nextPossibleFieldToMove)) {
                    possibleMoves.add(nextPossibleFieldToMove);
                }
            }

        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            Pair<Integer, Integer> possibleFieldToMove =
                    new Pair<>(Integer.valueOf(pawnsRow + 1), Integer.valueOf(pawnsCol));
            if (isFieldWithinBoard(possibleFieldToMove)) {
                if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                    possibleMoves.add(possibleFieldToMove);
                } else {
                    return possibleMoves;
                }
            } else {
                return possibleMoves;
            }

            if (pawnsRow.equals(Integer.valueOf(1))) {
                Pair<Integer, Integer> nextPossibleFieldToMove =
                        new Pair<>(Integer.valueOf(pawnsRow + 2), Integer.valueOf(pawnsCol));

                if (isFieldWithinBoard(nextPossibleFieldToMove) && isFieldEmpty(chessBoard, nextPossibleFieldToMove)) {
                    possibleMoves.add(nextPossibleFieldToMove);
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleCaptures(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> possibleCaptures = new HashSet<>();
        Integer pawnsRow = position.getKey();
        Integer pawnsCol = position.getValue();

        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            Pair<Integer, Integer> possibleFieldToCapture =
                    new Pair<>(Integer.valueOf(pawnsRow - 1), Integer.valueOf(pawnsCol) - 1);
            if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                    && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(ChessmanColourEnum.WHITE)) {
                possibleCaptures.add(possibleFieldToCapture);
            }

            Pair<Integer, Integer> nextPossibleFieldToCapture =
                    new Pair<>(Integer.valueOf(pawnsRow - 1), Integer.valueOf(pawnsCol) + 1);
            if (isFieldWithinBoardAndNotEmpty(chessBoard, nextPossibleFieldToCapture)
                    && chessBoard.getChessmanAt(nextPossibleFieldToCapture).getColour().equals(ChessmanColourEnum.WHITE)) {
                possibleCaptures.add(nextPossibleFieldToCapture);
            }

        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            Pair<Integer, Integer> possibleFieldToCapture =
                    new Pair<>(Integer.valueOf(pawnsRow + 1), Integer.valueOf(pawnsCol) - 1);
            if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                    && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(ChessmanColourEnum.BLACK)) {
                possibleCaptures.add(possibleFieldToCapture);
            }

            Pair<Integer, Integer> nextPossibleFieldToCapture =
                    new Pair<>(Integer.valueOf(pawnsRow + 1), Integer.valueOf(pawnsCol) + 1);
            if (isFieldWithinBoardAndNotEmpty(chessBoard, nextPossibleFieldToCapture)
                    && chessBoard.getChessmanAt(nextPossibleFieldToCapture).getColour().equals(ChessmanColourEnum.BLACK)) {
                possibleCaptures.add(nextPossibleFieldToCapture);
            }
        }
        return possibleCaptures;
    }

    private boolean isFieldWithinBoardAndNotEmpty(ChessBoard chessBoard,
                                                  Pair<Integer, Integer> nextPossibleFieldToCapture) {
        return isFieldWithinBoard(nextPossibleFieldToCapture) && !isFieldEmpty(chessBoard, nextPossibleFieldToCapture);
    }

    public boolean isPromotionAvailable(Pair<Integer, Integer> position) {
        Integer pawnsRow = position.getKey();
        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            return pawnsRow.equals(Integer.valueOf(0));
        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            return pawnsRow.equals(Integer.valueOf(7));
        }
        return false;
    }
}
