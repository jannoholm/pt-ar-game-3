package com.playtech.ptargame3.test.registry;


import com.playtech.ptargame3.common.task.Task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRegistryStub {

    public static final Logger logger = Logger.getLogger(GameRegistryStub.class.getName());

    private final ConcurrentHashMap<String, Task> games = new ConcurrentHashMap<>();

    private String getKey(String gameId, String clientId) {
        return gameId + "::" + clientId;
    }

    public Task getGameTask(String gameId, String clientId) {
        return games.get(getKey(gameId, clientId));
    }

    public void addGameTask(String gameId, String clientId, Task task) {
        games.put(getKey(gameId, clientId), task);
        logger.log(Level.FINE, () -> "Adding update listener to " + gameId + ":" + clientId);
    }

    public void removeGameTask(String gameId, String clientId) {
        games.remove(getKey(gameId, clientId));
        logger.log(Level.FINE, () -> "Removing update listener to " + gameId + ":" + clientId);
    }

}
