package com.playtech.ptargame3.server.registry;


import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.exception.CannotHostException;
import com.playtech.ptargame3.server.exception.GameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameRegistry {

    private final ConcurrentHashMap<String, GameRegistryGame> games = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, GameRegistryGame> hosting = new ConcurrentHashMap<>();

    public GameRegistryGame getGame(String gameId) {
        GameRegistryGame game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Game not found: " + gameId);
        }
        return game;
    }

    public synchronized String createGame(String clientId, String gameName, int players, boolean joinAsPlayer, String aiType) {
        if (hosting.get(clientId) != null) throw new CannotHostException("Client can host only one game. Open game: " + hosting.get(clientId));

        // create new game
        GameRegistryGame newGame = new GameRegistryGame(clientId, UUID.randomUUID().toString(), gameName, players, aiType);
        if (joinAsPlayer) {
            newGame.addPlayer(clientId, GameRegistryGame.Team.RED);
        }

        // store
        hosting.put(clientId, newGame);
        games.put(newGame.getGameId(), newGame);
        return newGame.getGameId();
    }

    public void joinPlayer(String gameId, String clientId) {
        joinPlayer(gameId, clientId, null);
    }

    public void joinPlayer(String gameId, String clientId, GameRegistryGame.Team team) {
        GameRegistryGame game = games.get(gameId);
        if (game == null) throw new GameNotFoundException("Game not found: " + gameId);

        game.addPlayer(clientId, team);
    }

    public void joinWatcher(String gameId, String clientId) {
        GameRegistryGame game = games.get(gameId);
        if (game == null) throw new GameNotFoundException("Game not found: " + gameId);

        game.addWatcher(clientId);
    }

    public Collection<GameRegistryGame> findGames(String filter, boolean all) {
        ArrayList<GameRegistryGame> matching = new ArrayList<>();
        for (GameRegistryGame game : games.values()) {
            if (!(game.getGameStatus() == GameRegistryGame.Status.COLLECTING || game.getGameStatus() == GameRegistryGame.Status.COLLECTING && all)) continue;
            if (!(game.isHostConnected())) continue;
            if (!(StringUtil.isNull(filter) || game.getGameName().contains(filter))) continue;
            if (!(game.getPlayers().size() < game.getPositions() || all)) continue;
            matching.add(game);
            if (matching.size() > 9) break;
        }
        return matching;
    }

    public void hostDisconnected(String clientId) {
        GameRegistryGame game = hosting.get(clientId);
        if (game != null) {
            game.setHostConnected(false);
        }
    }

    public void hostReconnected(String clientId) {
        GameRegistryGame game = hosting.get(clientId);
        if (game != null) {
            game.setHostConnected(true);
        }
    }

}
