package com.playtech.ptargame3.server.database.model;


import java.util.Collection;
import java.util.Collections;

public class Game {
    private final String gameId;
    private final int goalsRed;
    private final int goalsBlue;
    private final boolean suddenDeath;
    private final int gameTime;
    private final Collection<GamePlayerScore> playerScores;

    public Game(
            String gameId, int goalsRed, int goalsBlue, boolean suddenDeath,
            int gameTime, Collection<GamePlayerScore> playerScores) {
        this.gameId = gameId;
        this.goalsRed = goalsRed;
        this.goalsBlue = goalsBlue;
        this.suddenDeath = suddenDeath;
        this.gameTime = gameTime;
        this.playerScores = Collections.unmodifiableCollection(playerScores);
    }

    public String getGameId() {
        return gameId;
    }

    public int getGoalsRed() {
        return goalsRed;
    }

    public int getGoalsBlue() {
        return goalsBlue;
    }

    public boolean isSuddenDeath() {
        return suddenDeath;
    }

    public int getGameTime() {
        return gameTime;
    }

    public Collection<GamePlayerScore> getPlayerScores() {
        return playerScores;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", goalsRed=" + goalsRed +
                ", goalsBlue=" + goalsBlue +
                ", suddenDeath=" + suddenDeath +
                ", gameTime=" + gameTime +
                ", playerScores=" + playerScores +
                '}';
    }
}
