package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.database.model.Game;
import com.playtech.ptargame3.server.exception.SystemException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameDatabaseImpl implements GameDatabase {

    private static final Logger logger = Logger.getLogger(GameDatabaseImpl.class.getName());

    private static final String TABLE_GAMES = "GAMES";

    private final DatabaseAccessImpl dbInit;

    private List<Game> leaderboard = new ArrayList<>();

    public GameDatabaseImpl(DatabaseAccessImpl dbInit) {
        this.dbInit = dbInit;
    }

    protected void init() {
        Connection connection = dbInit.allocateConnection();
        try (Statement stmt = connection.createStatement()) {
            // table users
            String sql =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES + " " +
                            "(ID INT PRIMARY KEY     NOT NULL, " +
                            " USERID         INT     NOT NULL, " +
                            " TODO          TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            logger.info("Games database created!");

            sql = "select ID, USERID, TODO from " + TABLE_GAMES;
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("ID");
                int userId = result.getInt("USERID");
                leaderboard.add(new Game(id, userId));
            }
            logger.info("Games read to memory!");
        } catch (Exception e) {
            throw new SystemException("Unable to initialize users database", e);
        } finally {
            dbInit.releaseConnection(connection);
        }
    }

}
