package com.killerchess.core.chessmans;

import com.killerchess.core.chessboard.ChessBoard;
import org.springframework.data.util.Pair;

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
    public Integer getPointsValue() {
        return HORSE_VALUE;
    }

    @Override
    public Set<Pair<Integer, Integer>> getPossibleMoves(ChessBoard chessBoard, Pair<Integer, Integer> position) {
        var possibleMoves = new HashSet<Pair<Integer, Integer>>();
        var rookRow = position.getFirst();
        var rookCol = position.getSecond();

        Pair<Integer, Integer> possibleFieldToMove;

        // 1
        possibleFieldToMove = Pair.of(rookRow - 2, rookCol + 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 2
        possibleFieldToMove = Pair.of(rookRow - 2, rookCol - 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 3
        possibleFieldToMove = Pair.of(rookRow - 1, rookCol + 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 4
        possibleFieldToMove = Pair.of(rookRow - 1, rookCol - 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 5
        possibleFieldToMove = Pair.of(rookRow + 1, rookCol - 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 6
        possibleFieldToMove = Pair.of(rookRow + 1, rookCol + 2);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 7
        possibleFieldToMove = Pair.of(rookRow + 2, rookCol + 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);
        // 8
        possibleFieldToMove = Pair.of(rookRow + 2, rookCol - 1);
        if (isFieldWithinBoardAndEmpty(chessBoard, possibleFieldToMove))
            possibleMoves.add(possibleFieldToMove);

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

        // 1
        possibleFieldToCapture = Pair.of(rookRow - 2, rookCol + 1);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 2
        possibleFieldToCapture = Pair.of(rookRow - 2, rookCol - 1);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 3
        possibleFieldToCapture = Pair.of(rookRow - 1, rookCol + 2);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 4
        possibleFieldToCapture = Pair.of(rookRow - 1, rookCol - 2);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 5
        possibleFieldToCapture = Pair.of(rookRow + 1, rookCol - 2);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 6
        possibleFieldToCapture = Pair.of(rookRow + 1, rookCol + 2);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 7
        possibleFieldToCapture = Pair.of(rookRow + 2, rookCol + 1);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);
        // 8
        possibleFieldToCapture = Pair.of(rookRow + 2, rookCol - 1);
        if (isCapturePossible(chessBoard, colorToCapture, possibleFieldToCapture))
            possibleCaptures.add(possibleFieldToCapture);

        return possibleCaptures;
    }

    private boolean isCapturePossible(ChessBoard chessBoard,
                                      ChessmanColourEnum colorToCapture,
                                      Pair<Integer, Integer> possibleFieldToCapture) {
        return isFieldWithinBoardAndNotEmpty(chessBoard, possibleFieldToCapture)
                && chessBoard.getChessmanAt(possibleFieldToCapture).getColour().equals(colorToCapture);
    }
}
