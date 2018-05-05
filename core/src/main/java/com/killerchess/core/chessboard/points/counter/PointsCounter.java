package com.killerchess.core.chessboard.points.counter;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.ChessmanColourEnum;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class PointsCounter {

    private StateInterpreter stateInterpreter;

    public PointsCounter() {
        //TODO AK Inject StateInterpreter when Injection available
        stateInterpreter = new StateInterpreter();
    }

    public Integer countWhitePlayerPoints(String jsonInputBoard, String jsonOutputBoard) throws IOException {
        int defaultPoints = countPointsOnBoardForColour(jsonInputBoard, ChessmanColourEnum.BLACK);
        int finalPoints = countPointsOnBoardForColour(jsonOutputBoard, ChessmanColourEnum.BLACK);
        return defaultPoints - finalPoints;
    }

    public Integer countBlackPlayerPoints(String jsonInputBoard, String jsonOutputBoard) throws IOException {
        int defaultPoints = countPointsOnBoardForColour(jsonInputBoard, ChessmanColourEnum.WHITE);
        int finalPoints = countPointsOnBoardForColour(jsonOutputBoard, ChessmanColourEnum.WHITE);
        return defaultPoints - finalPoints;
    }

    private int countPointsOnBoardForColour(String jsonBoard, ChessmanColourEnum colour) throws IOException {
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(jsonBoard).getChessBoardCopy();
        AtomicInteger points = new AtomicInteger(0);
        chessBoard.forEach(currentRow -> currentRow.stream()
                .filter(chessman -> chessman.getColour().equals(colour))
                .forEach(chessman -> points.addAndGet(chessman.getPointsValue())));
        return points.get();
    }

}
