package com.killerchess.view.game;

public enum ChessmanTypeEnum {
    PAWN(0), BISHOP(1), HORSE(2), ROOK(3), QUEEN(4), KING(5), EMPTY(6);

    final int moveDir;

    static ChessmanTypeEnum getTypeFromSymbol(Character chessmanCharacter){
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

    public Character getSymbolFromType(){
        switch(this) {
            case BISHOP:
                return 'B';
            case PAWN:
                return 'P';
            case HORSE:
                return 'H';
            case ROOK:
                return 'R';
            case QUEEN:
                return 'Q';
            case KING:
                return 'K';
            default:
                return 'X';
        }
    }
    ChessmanTypeEnum(int moveDir){
        this.moveDir = moveDir;
    }
}
