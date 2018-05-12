package com.killerchess.core.chessboard;

import com.killerchess.core.chessmans.Chessman;
import javafx.util.Pair;

import java.util.ArrayList;

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
}
