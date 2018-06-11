package com.killerchess.core.chessboard;

import com.killerchess.core.chessmans.Chessman;
import com.killerchess.core.chessmans.ChessmanColourEnum;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public Pair<Integer, Integer> getChessmanPosition(Chessman chessmanToSearch) {
        for (ArrayList<Chessman> chessmans : chessBoard) {
            for (Chessman chessman : chessmans) {
                if (chessman.equals(chessmanToSearch))
                    return new Pair<>(chessBoard.indexOf(chessmans), chessmans.indexOf(chessman));
            }
        }
        return null;
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
        var chessmansWithGivenColor = new HashSet<Chessman>();

        for (ArrayList<Chessman> chessmans : chessBoard) {
            for (Chessman chessman : chessmans) {
                if (chessman.getColour().equals(color))
                    chessmansWithGivenColor.add(chessman);
            }
        }

        return chessmansWithGivenColor;
    }
}
