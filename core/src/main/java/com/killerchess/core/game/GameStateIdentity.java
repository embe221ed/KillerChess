/*
package com.killerchess.core.game;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class GameStateIdentity implements Serializable {

    private Integer gameStateNumber;
    private Game game;

    @NotNull
    @Column(name = "game_state_number")
    public int getGameStateNumber() {
        return gameStateNumber;
    }

    public void setGameStateNumber(int gameStateNumber) {
        this.gameStateNumber = gameStateNumber;
    }

    @OneToOne
    @JoinColumn(name = "game_id")
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateIdentity that = (GameStateIdentity) o;
        if (!game.equals(that.game)) return false;
        return gameStateNumber.equals(that.gameStateNumber);
    }
}
*/
