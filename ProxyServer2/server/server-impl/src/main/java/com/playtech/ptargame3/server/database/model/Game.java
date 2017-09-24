package com.playtech.ptargame3.server.database.model;


public class Game {

    private final int id;
    private final int userId;

    public Game(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }

}
