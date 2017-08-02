package com.playtech.ptargame3.server.registry;


import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.exception.CannotJoinException;
import com.playtech.ptargame3.server.exception.SystemException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameRegistryGame {

    public enum Status {
        COLLECTING,
        PLAYING,
        FINISHED
    }

    public enum Team {
        RED,
        BLUE
    }

    private final String gameId;
    private final String gameName;
    private final int positions;
    private final String aiType;
    private volatile Collection<GameRegistryGamePlayer> players;
    private volatile Collection<String> subscribers;
    private Status gameStatus;
    private Team winner;

    public GameRegistryGame(String gameId, String gameName, int positions, String aiType) {
        this.aiType = aiType;
        if (positions%2 != 0) throw new SystemException("Invalid player count. Has to be even. Was: " + positions);

        this.gameId = gameId;
        this.gameName = gameName;
        this.positions = positions;
        this.players = Collections.unmodifiableCollection(new ArrayList<>());
        this.subscribers = Collections.unmodifiableCollection(new ArrayList<>());
        this.gameStatus = Status.COLLECTING;
    }

    public String getGameId() {
        return gameId;
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    public String getGameName() {
        return gameName;
    }

    public int getPositions() {
        return positions;
    }

    public String getAiType() {
        return aiType;
    }

    public Team getWinner() {
        return winner;
    }

    public Collection<GameRegistryGamePlayer> getPlayers() {
        return this.players;
    }

    public Collection<String> getSubscribers() {
        return subscribers;
    }

    public synchronized void addWatcher(String clientId) {
        if (StringUtil.isNull(clientId)) throw new SystemException("clientId not set");

        // subscribe to updates
        addSubscriber(clientId);
    }

    public synchronized void addPlayer(String clientId, Team team) {
        // validate input and game status
        if (StringUtil.isNull(clientId)) throw new SystemException("clientId not set");
        if (gameStatus != Status.COLLECTING) throw new CannotJoinException("Game status " + gameStatus);
        if (players.size() >= positions) throw new CannotJoinException("Player limit reached: " + positions);

        // check if player already joined. idempotent calls are allowed
        for (GameRegistryGamePlayer player : players) {
            if (player.getClientId().equals(clientId)) {
                return;
            }
        }

        // validate team sizes
        int redTeam = 0;
        int blueTeam = 0;
        for (GameRegistryGamePlayer player : players) {
            if (player.getTeam() == Team.BLUE) {
                blueTeam++;
            } else if (player.getTeam() == Team.RED) {
                redTeam++;
            }
        }
        if (team == null) {
            // assign smaller team
            team = blueTeam < redTeam ? Team.BLUE : Team.RED;
        }
        if (team == Team.RED && positions/2 < redTeam+1 || team == Team.BLUE && positions/2 < blueTeam+1) {
            throw new CannotJoinException("Team size limit reached: " + team + ":" + positions + "::" + blueTeam + "+" + redTeam);
        }

        // add player to game
        ArrayList<GameRegistryGamePlayer> newplayers = new ArrayList<>(players);
        newplayers.add(new GameRegistryGamePlayer(clientId, team, (byte)((team == Team.RED ? redTeam : blueTeam)+1)));
        this.players = Collections.unmodifiableCollection(newplayers);

        // subscribe to updates
        addSubscriber(clientId);
    }

    private synchronized void addSubscriber(String clientId) {
        if (subscribers.contains(clientId)) return;

        ArrayList<String> newSubscribers = new ArrayList<>(subscribers);
        newSubscribers.add(clientId);
        this.subscribers = Collections.unmodifiableCollection(newSubscribers);
    }

    public synchronized void changeStatusToPlaying() {
        if (gameStatus == Status.PLAYING) return;
        if (gameStatus != Status.COLLECTING) throw new SystemException("Invalid attempt to change game status: " + gameStatus + "->" + Status.PLAYING);
        if (positions == players.size()) throw new SystemException("Not all players have joined. Expected: " + positions + ", actual: " + players.size());

        gameStatus = Status.PLAYING;
    }

    public synchronized void changeStatusToFinished(Team winner) {
        if (gameStatus == Status.FINISHED) return;
        if (gameStatus != Status.PLAYING) throw new SystemException("Invalid attempt to change game status: " + gameStatus + "->" + Status.PLAYING);

        this.winner = winner;
    }

    @Override
    public String toString() {
        return "GameRegistryGame{" +
                "positions=" + positions +
                ", players=" + players +
                ", gameStatus=" + gameStatus +
                ", winner=" + winner +
                '}';
    }
}
