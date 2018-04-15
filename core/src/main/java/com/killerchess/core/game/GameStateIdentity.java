package com.killerchess.core.game;
import com.sun.istack.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

public class GameStateIdentity implements Serializable {

    public int getGameStateNumber() {
        return gameStateNumber;
    }

    public void setGameStateNumber(int gameStateNumber) {
        this.gameStateNumber = gameStateNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @NotNull
    private Integer gameStateNumber;

    @NotNull
    private Game game;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameStateIdentity that = (GameStateIdentity) o;

        if (!game.equals(that.game)) return false;
        return gameStateNumber.equals(that.gameStateNumber);
    }
}
