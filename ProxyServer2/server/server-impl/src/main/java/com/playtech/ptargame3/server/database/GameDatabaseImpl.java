package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.database.model.Game;
import com.playtech.ptargame3.server.database.model.GamePlayerScore;
import com.playtech.ptargame3.server.exception.SystemException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDatabaseImpl implements GameDatabase {

    private static final Logger logger = Logger.getLogger(GameDatabaseImpl.class.getName());

    private static final String TABLE_GAMES = "GAMES";
    private static final String TABLE_SCORE = "GAMES_PLAYER_SCORE";

    private final DatabaseAccessImpl dbInit;
    private final ScheduledExecutorService executor;
    private ScheduledFuture maintenanceFuture;

    private ArrayList<Game> pendingWrites = new ArrayList<>();

    public GameDatabaseImpl(DatabaseAccessImpl dbInit, ScheduledExecutorService executor) {
        this.dbInit = dbInit;
        this.executor = executor;
    }

    protected void init() {
        createTable();
        setupMaintenance();
    }

    protected void createTable() {
        Connection connection = dbInit.allocateConnection();
        try (Statement stmt = connection.createStatement()) {
            // table games
            String sql =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES + " " +
                    "(ID INT PRIMARY KEY     NOT NULL, " +
                    " USERID         INT     NOT NULL, " +
                    " TODO          TEXT    NOT NULL)";
            stmt.executeUpdate(sql);

            // recreate
            boolean recreate = true;
            sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" +TABLE_SCORE+ "'";
            try (ResultSet result = stmt.executeQuery(sql)) {
                if (result.next()) {
                    recreate = false;
                }
            }

            if (recreate) {
                sql = "DROP TABLE " + TABLE_GAMES;
                stmt.executeUpdate(sql);

                // table games
                sql =
                        "CREATE TABLE IF NOT EXISTS " + TABLE_GAMES + " " +
                        "(GAMEID         TEXT    PRIMARY KEY NOT NULL, " +
                        " GOALS_RED      INT     NOT NULL, " +
                        " GOALS_BLUE     INT     NOT NULL, " +
                        " SUDDEN_DEATH   INT     NOT NULL, " +
                        " GAME_TIME      INT     NOT NULL)";
                stmt.executeUpdate(sql);

                // table player score
                sql =
                        "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE + " " +
                        "(GAMEID         TEXT    NOT NULL, " +
                        " USERID         INT     NOT NULL, " +
                        " TEAM           INT     NOT NULL, " +
                        " GOALS          INT     NOT NULL, " +
                        " BULLET_HITS    INT     NOT NULL, " +
                        " BALL_TOUCHES   INT     NOT NULL, " +
                        " BOOST_TOUCHES  INT     NOT NULL, " +
                        " SCORE          INT     NOT NULL, " +
                        " ELO_RATING     INT     NOT NULL, " +
                        " LEADERBOARD_POS INT     NOT NULL)";
                stmt.executeUpdate(sql);
            }
            logger.info("Games database created!");
        } catch (Exception e) {
            throw new SystemException("Unable to initialize games database", e);
        } finally {
            dbInit.releaseConnection(connection);
        }
    }

    private void setupMaintenance() {
        if (maintenanceFuture == null) {
            maintenanceFuture = executor.scheduleAtFixedRate(() -> {
                List<Game> todo = new ArrayList<>();
                synchronized (this) {
                    todo.addAll(pendingWrites);
                }
                if (todo.size() > 0) {
                    String insertGameSql = "insert into " + TABLE_GAMES + " (GAMEID, GOALS_RED, GOALS_BLUE, SUDDEN_DEATH, GAME_TIME) values (?, ?, ?, ?, ?)";
                    String insertScoreSql = "insert into " + TABLE_SCORE + " (GAMEID, USERID, TEAM, GOALS, BULLET_HITS, BALL_TOUCHES, BOOST_TOUCHES, SCORE, ELO_RATING, LEADERBOARD_POS) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    Connection connection = dbInit.allocateConnection();
                    try (
                            PreparedStatement insertGameStmt = connection.prepareStatement(insertGameSql);
                            PreparedStatement insertScoreStmt = connection.prepareStatement(insertScoreSql);
                    ) {
                        for (Game game : todo) {
                            insertGameStmt.setString(1, game.getGameId());
                            insertGameStmt.setInt(2, game.getGoalsRed());
                            insertGameStmt.setInt(3, game.getGoalsBlue());
                            insertGameStmt.setInt(4, game.isSuddenDeath() ? 1 : 0);
                            insertGameStmt.setInt(5, game.getGameTime());
                            insertGameStmt.executeUpdate();

                            for (GamePlayerScore score : game.getPlayerScores()) {
                                insertScoreStmt.setString(1, game.getGameId());
                                insertScoreStmt.setInt(2, score.getUserId());
                                insertScoreStmt.setInt(3, score.getTeam().ordinal());
                                insertScoreStmt.setInt(4, score.getGoals());
                                insertScoreStmt.setInt(5, score.getBulletHits());
                                insertScoreStmt.setInt(6, score.getBallTouches());
                                insertScoreStmt.setInt(7, score.getBoostTouches());
                                insertScoreStmt.setInt(8, score.getScore());
                                insertScoreStmt.setInt(9, score.getEloRating());
                                insertScoreStmt.setInt(10, score.getLeaderboardPos());
                                insertScoreStmt.addBatch();
                            }
                            insertScoreStmt.executeBatch();
                            logger.info("Game written to database: " + game);
                            synchronized (this) {
                                pendingWrites.remove(game);
                            }
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Unable to write to games database", e);
                        throw new SystemException("Unable to write to games database", e);
                    } finally {
                        dbInit.releaseConnection(connection);
                    }
                }
            }, 1000, 1082, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Already started callback handler.");
        }
    }

    @Override
    public void addGame(Game game) {
        synchronized (this) {
            this.pendingWrites.add(game);
        }
    }
}
