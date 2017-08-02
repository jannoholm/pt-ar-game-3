package com.playtech.ptargame3.server.task;

import com.playtech.ptargame3.common.task.TaskInput;

import java.util.concurrent.atomic.AtomicInteger;


public class GameUpdateTaskInput implements TaskInput {

    private static final AtomicInteger counter = new AtomicInteger(1000);

    private final String type;
    private final String id;
    private final String gameId;
    private final String clientId;

    public GameUpdateTaskInput(String type, String gameId, String clientId) {
        this.type = type;
        this.gameId = gameId;
        this.clientId = clientId;
        this.id = counter.incrementAndGet() + "-" + gameId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getGameId() {
        return gameId;
    }

    public String getClientId() {
        return clientId;
    }
}
