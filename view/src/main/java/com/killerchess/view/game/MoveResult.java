package com.killerchess.view.game;

public class MoveResult {

    private MoveType moveType;
    private ChessmanImage chessmanImage;

    public MoveType getMoveType() {
        return moveType;
    }

    public ChessmanImage getChessmanImage() {
        return chessmanImage;
    }

    public MoveResult(MoveType movetype){
        this(movetype, null);
    }

    public MoveResult(MoveType moveType, ChessmanImage chessmanImage){
        this.moveType = moveType;
        this.chessmanImage = chessmanImage;
    }
}
