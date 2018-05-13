package com.killerchess.core.chessboard.points.counter;

import com.killerchess.core.chessboard.state.interpreter.StateInterpreter;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PointsCounter {

    private final StateInterpreter stateInterpreter;

    @Autowired
    public PointsCounter(StateInterpreter stateInterpreter) {
        this.stateInterpreter = stateInterpreter;
    }

    public Integer countWhitePlayerPoints(String jsonInputBoard, String jsonOutputBoard) {
        var defaultPoints = countPointsOnBoardForColour(jsonInputBoard, ChessmanColourEnum.BLACK);
        var finalPoints = countPointsOnBoardForColour(jsonOutputBoard, ChessmanColourEnum.BLACK);
        return defaultPoints - finalPoints;
    }

    public Integer countBlackPlayerPoints(String jsonInputBoard, String jsonOutputBoard) {
        int defaultPoints = countPointsOnBoardForColour(jsonInputBoard, ChessmanColourEnum.WHITE);
        int finalPoints = countPointsOnBoardForColour(jsonOutputBoard, ChessmanColourEnum.WHITE);
        return defaultPoints - finalPoints;
    }

    private int countPointsOnBoardForColour(String jsonBoard, ChessmanColourEnum colour) {
        var chessBoard = stateInterpreter.convertJsonBoardToChessBoard(jsonBoard).getChessBoardCopy();
        var points = new AtomicInteger(0);
        chessBoard.forEach(currentRow -> currentRow.stream()
                .filter(chessman -> chessman.getColour().equals(colour))
                .forEach(chessman -> points.addAndGet(chessman.getPointsValue())));
        return points.get();
    }

}
