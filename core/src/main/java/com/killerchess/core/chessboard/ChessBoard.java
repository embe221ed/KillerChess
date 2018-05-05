package com.killerchess.core.chessboard;

import com.killerchess.core.chessmans.Chessman;

import java.util.ArrayList;

public class ChessBoard {

    private ArrayList<ArrayList<Chessman>> chessBoard;

    public ChessBoard(ArrayList<ArrayList<Chessman>> chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Chessman getChessmanAt(int row, int col) {
        return chessBoard.get(row).get(col);
    }

    public int getChessBoardColumnsSize() {
        return chessBoard.size();
    }

    public int getChessBoardRowsSize() {
        return chessBoard.get(0).size();
    }

}
