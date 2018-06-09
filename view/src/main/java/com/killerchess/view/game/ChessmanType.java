package com.killerchess.view.game;

public enum ChessmanType {
    PAWN(0), BISHOP(1), HORSE(2), ROOK(3), QUEEN(4), KING(5), EMPTY(6);

    final int moveDir;

    static ChessmanType getTypeFromSymbol(Character chessmanCharacter){
        switch(chessmanCharacter) {
            case 'B':
                return BISHOP;
            case 'P':
                return PAWN;
            case 'H':
                return HORSE;
            case 'R':
                return ROOK;
            case 'Q':
                return QUEEN;
            case 'K':
                return KING;
            default:
                return EMPTY;
        }
    }
    ChessmanType(int moveDir){
        this.moveDir = moveDir;
    }
}
