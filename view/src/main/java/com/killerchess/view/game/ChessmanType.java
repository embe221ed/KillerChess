package com.killerchess.view.game;

public enum ChessmanType {
    PAWN(0), BISHOP(1), HORSE(2), ROOK(3), QUEEN(4), KING(5);

    final int moveDir;

    ChessmanType(int moveDir){
        this.moveDir = moveDir;
    }
}
