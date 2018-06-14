package com.killerchess.core.chessboard;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ChessBoard {

    private ArrayList<ArrayList<Chessman>> chessBoard;

    public ChessBoard(ArrayList<ArrayList<Chessman>> chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Chessman getChessmanAt(int row, int col) {
        return chessBoard.get(row).get(col);
    }

    public Chessman getChessmanAt(Pair<Integer, Integer> position) {
        int row = position.getKey();
        int col = position.getValue();
        return getChessmanAt(row, col);
    }

    public int getChessBoardColumnsSize() {
        return chessBoard.size();
    }

    public int getChessBoardRowsSize() {
        return chessBoard.get(0).size();
    }

    public ArrayList<ArrayList<Chessman>> getChessBoardCopy() {
        return new ArrayList<>(chessBoard);
    }


    public Set<Chessman> getAllChessmansWithGivenColor(ChessmanColourEnum color) {
        var chessmenWithGivenColor = new HashSet<Chessman>();

        chessBoard.forEach(chessmen -> chessmenWithGivenColor.addAll(chessmen.stream()
                .filter(chessman -> chessman.getColour().equals(color))
                .collect(Collectors.toSet()))
        );

        return chessmenWithGivenColor;
    }

    public Map<ChessmanColourEnum, Boolean> checkGameBoardForChessmen() {
        Map<ChessmanColourEnum, Boolean> map = new HashMap<>();
        map.put(ChessmanColourEnum.WHITE, false);
        map.put(ChessmanColourEnum.BLACK, false);
        map.put(ChessmanColourEnum.EMPTY, false);
        for (var row : chessBoard) {
            for (var chessman : row) {
                if (!map.get(chessman.getColour()) && !chessman.getSymbol().equals('X'))
                    map.put(chessman.getColour(), true);
                else if (map.get(ChessmanColourEnum.BLACK) && map.get(ChessmanColourEnum.WHITE))
                    return map;
            }
        }
        return map;
    }

    public Pair<Integer, Integer> getChessmanPosition(Chessman chessmanToSearch) {
        for (ArrayList<Chessman> chessmen : chessBoard) {
            for (Chessman chessman : chessmen) {
                if (chessman.equals(chessmanToSearch)) {
                    return new Pair<>(chessBoard.indexOf(chessmen), chessmen.indexOf(chessman));
                }
            }
        }

        return null;
    }
}
