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
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
        var pawnsRow = position.getKey();
        var pawnsCol = position.getValue();

        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            Pair<Integer, Integer> possibleFieldToMove = new Pair<>(pawnsRow - 1, pawnsCol);
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
                Pair<Integer, Integer> nextPossibleFieldToMove = new Pair<>(pawnsRow - 2, pawnsCol);
                if (isFieldWithinBoard(nextPossibleFieldToMove) && isFieldEmpty(chessBoard, nextPossibleFieldToMove)) {
                    possibleMoves.add(nextPossibleFieldToMove);
                }
            }

        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            Pair<Integer, Integer> possibleFieldToMove = new Pair<>(pawnsRow + 1, pawnsCol);
            if (isFieldWithinBoard(possibleFieldToMove)) {
                if (isFieldEmpty(chessBoard, possibleFieldToMove)) {
                    possibleMoves.add(possibleFieldToMove);
                } else {
                    return possibleMoves;
                }
            } else {
                return possibleMoves;
            }

            if (pawnsRow.equals(1)) {
                Pair<Integer, Integer> nextPossibleFieldToMove = new Pair<>(pawnsRow + 2, pawnsCol);
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
        var pawnsRow = position.getKey();
        var pawnsCol = position.getValue();

        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            Pair<Integer, Integer> possibleFieldToCapture = new Pair<>(pawnsRow - 1, pawnsCol - 1);
            addPossibleCaptureIfWithinBoardAndMatchesColour(chessBoard, possibleCaptures, possibleFieldToCapture,
                    ChessmanColourEnum.WHITE);

            Pair<Integer, Integer> nextPossibleFieldToCapture = new Pair<>(pawnsRow - 1, pawnsCol + 1);
            addPossibleCaptureIfWithinBoardAndMatchesColour(chessBoard, possibleCaptures, nextPossibleFieldToCapture,
                    ChessmanColourEnum.WHITE);

        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            Pair<Integer, Integer> possibleFieldToCapture = new Pair<>(pawnsRow + 1, pawnsCol - 1);
            addPossibleCaptureIfWithinBoardAndMatchesColour(chessBoard, possibleCaptures, possibleFieldToCapture,
                    ChessmanColourEnum.BLACK);

            Pair<Integer, Integer> nextPossibleFieldToCapture = new Pair<>(pawnsRow + 1, pawnsCol + 1);
            addPossibleCaptureIfWithinBoardAndMatchesColour(chessBoard, possibleCaptures, possibleFieldToCapture,
                    ChessmanColourEnum.BLACK);
        }
        return possibleCaptures;
    }

    public boolean isPromotionAvailable(Pair<Integer, Integer> position) {
        var pawnsRow = position.getKey();
        if (getColour().equals(ChessmanColourEnum.BLACK)) {
            return pawnsRow.equals(0);
        } else if (getColour().equals(ChessmanColourEnum.WHITE)) {
            return pawnsRow.equals(7);
        }
        return false;
    }

    @Override
    public Integer getPointsValue() {
        return PAWN_VALUE;
    }

    private void addPossibleCaptureIfWithinBoardAndMatchesColour(ChessBoard chessBoard,
                                                                 Set<Pair<Integer, Integer>> possibleCaptures,
                                                                 Pair<Integer, Integer> possibleFieldToCapture,
                                                                 ChessmanColourEnum colour) {
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colour)) {
            possibleCaptures.add(possibleFieldToCapture);
        }
    }

    private boolean isFieldWithinBoardAndNotEmpty(ChessBoard chessBoard,
                                                  Pair<Integer, Integer> nextPossibleFieldToCapture) {
        return isFieldWithinBoard(nextPossibleFieldToCapture) && !isFieldEmpty(chessBoard, nextPossibleFieldToCapture);
    }
}
