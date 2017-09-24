package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.exception.SystemException;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;

public class DatabaseAccessImpl implements DatabaseAccess {

    private static final String DB_FILE="db/game.db";

    private final ScheduledExecutorService executor;
    private Connection connection = null;
    private boolean initialized = false;

    private UserDatabaseImpl userDatabase;
    private GameDatabaseImpl gameDatabase;

    public DatabaseAccessImpl(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public void setup() throws Exception {
        new File(DB_FILE).getParentFile().mkdirs();
        Class.forName("org.sqlite.JDBC");
        userDatabase = new UserDatabaseImpl(this, executor);
        gameDatabase = new GameDatabaseImpl(this);
        initialized = true;
        userDatabase.init();
        gameDatabase.init();
    }

    protected synchronized Connection allocateConnection() {
        if (!initialized) throw new SystemException("Database not initialized!");

        // we allow only one connection
        long timeout = 5000;
        long time = System.currentTimeMillis();
        while (connection != null && System.currentTimeMillis()-time < timeout) {
            try {
                wait(timeout-(System.currentTimeMillis()-time));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // return connection if possible
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
                return connection;
            } catch (SQLException e) {
                throw new SystemException("Error allocating connection!", e);
            }
        }

        throw new SystemException("Unable to allocate connection!");
    }

    protected synchronized void releaseConnection( Connection connection ) {
        if ( this.connection == connection ) {
            try {
                this.connection.close();
            } catch (SQLException ignored) {}
            this.connection = null;
        }
        notifyAll();
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public GameDatabase getGameDatabase() {
        return gameDatabase;
    }
}
