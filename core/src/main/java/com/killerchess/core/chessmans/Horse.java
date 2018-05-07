package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import javafx.util.Pair;

import java.util.HashSet;
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
        Set<Pair<Integer, Integer>> possibleMoves = new HashSet<>();
        Integer rookRow = position.getKey();
        Integer rookCol = position.getValue();

        Pair<Integer, Integer> possibleFieldToMove;

        // 1
        possibleFieldToMove = new Pair<>(rookRow - 2, rookCol + 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 2
        possibleFieldToMove = new Pair<>(rookRow - 2, rookCol - 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 3
        possibleFieldToMove = new Pair<>(rookRow - 1, rookCol + 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 4
        possibleFieldToMove = new Pair<>(rookRow - 1, rookCol - 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 5
        possibleFieldToMove = new Pair<>(rookRow + 1, rookCol - 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 6
        possibleFieldToMove = new Pair<>(rookRow + 1, rookCol + 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 7
        possibleFieldToMove = new Pair<>(rookRow + 2, rookCol + 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 8
        possibleFieldToMove = new Pair<>(rookRow + 2, rookCol - 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);

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

        // 1
        possibleFieldToCapture = new Pair<>(rookRow - 2, rookCol + 1);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 2
        possibleFieldToCapture = new Pair<>(rookRow - 2, rookCol - 1);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 3
        possibleFieldToCapture = new Pair<>(rookRow - 1, rookCol + 2);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 4
        possibleFieldToCapture = new Pair<>(rookRow - 1, rookCol - 2);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 5
        possibleFieldToCapture = new Pair<>(rookRow + 1, rookCol - 2);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 6
        possibleFieldToCapture = new Pair<>(rookRow + 1, rookCol + 2);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 7
        possibleFieldToCapture = new Pair<>(rookRow + 2, rookCol + 1);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 8
        possibleFieldToCapture = new Pair<>(rookRow + 2, rookCol - 1);
        if (isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture))
            possibleCaptures.add(possibleFieldToCapture);

        return possibleCaptures;
    }

    @Override
    public Integer getPointsValue() {
        return HORSE_VALUE;
    }
}
