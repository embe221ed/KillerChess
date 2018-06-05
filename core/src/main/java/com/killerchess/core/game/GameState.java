package com.killerchess.core.game;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "game_state")
public class GameState {

    private String state;

    private int gameStateNumber;
    private Game game;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_state_number")
    public int getGameStateNumber() {
        return gameStateNumber;
    }

    public void setGameStateNumber(int gameStateNumber) {
        this.gameStateNumber = gameStateNumber;
    }

    @ManyToOne
    @JoinColumn(name = "game_id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name = "state", length = 1024)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(state, gameState.state) &&
                Objects.equals(gameStateNumber, gameState.gameStateNumber) &&
                Objects.equals(game, gameState.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, gameStateNumber, game);
    }
}
