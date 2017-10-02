package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.exception.SystemException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class UserDatabaseImpl implements UserDatabase {

    private static final Logger logger = Logger.getLogger(UserDatabaseImpl.class.getName());

    private static final String TABLE_USERS = "USERS";
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final DatabaseAccessImpl dbInit;
    private final ScheduledExecutorService executor;
    private ScheduledFuture maintenanceFuture;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> pendingWrites = new ArrayList<>();

    public UserDatabaseImpl(DatabaseAccessImpl dbInit, ScheduledExecutorService executor) {
        this.dbInit = dbInit;
        this.executor = executor;
    }

    protected void init() {
        createTable();
        readTables();
        setupMaintenance();
    }

    private void createTable() {
        Connection connection = dbInit.allocateConnection();
        try (Statement stmt = connection.createStatement()) {
            // table users
            String sql =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " " +
                            "(ID INT PRIMARY KEY     NOT NULL, " +
                            " NAME           TEXT    NOT NULL, " +
                            " EMAIL          TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            logger.info("Users database created!");
        } catch (Exception e) {
            throw new SystemException("Unable to initialize users database", e);
        } finally {
            dbInit.releaseConnection(connection);
        }
    }

    private void readTables() {
        Connection connection = dbInit.allocateConnection();
        try (Statement stmt = connection.createStatement()) {
            String sql = "select ID, NAME, EMAIL from " + TABLE_USERS + " order by id";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("NAME");
                String email = result.getString("EMAIL");
                User user = new User(id, name, email);
                users.add(user);
                logger.info("User read from database: " + user);
                if (idGenerator.get() < id) {
                    idGenerator.set(id);
                }
            }
            logger.info("Users read to memory!");
        } catch (Exception e) {
            throw new SystemException("Unable to initialize users database", e);
        } finally {
            dbInit.releaseConnection(connection);
        }
    }

    private void setupMaintenance() {
        if (maintenanceFuture == null) {
            maintenanceFuture = executor.scheduleAtFixedRate(() -> {
                List<User> todo = new ArrayList<>();
                synchronized (this) {
                    todo.addAll(pendingWrites);
                }
                if (todo.size() > 0) {
                    String insertSql = "insert into " + TABLE_USERS + " (ID, NAME, EMAIL) VALUES (?, ?, ?)";
                    Connection connection = dbInit.allocateConnection();
                    try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                        for (User user : todo) {
                            stmt.setInt(1, user.getId());
                            stmt.setString(2, user.getName());
                            stmt.setString(3, user.getEmail());
                            stmt.executeUpdate();
                            logger.info("User written to database: " + user);
                            synchronized (this) {
                                pendingWrites.remove(user);
                            }
                        }
                    } catch (Exception e) {
                        throw new SystemException("Unable to initialize users database", e);
                    } finally {
                        dbInit.releaseConnection(connection);
                    }
                }
            }, 1000, 1075, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Already started callback handler.");
        }
    }

    public void addUser(String name, String email) {
        if (StringUtil.isNull(name)) throw new NullPointerException("Name cannot be null.");
        User user = new User(idGenerator.incrementAndGet(), name, email);
        synchronized (this) {
            pendingWrites.add(user);
            users.add(user);
        }
    }

    public Collection<User> getUsers() {
        synchronized (this) {
            return Collections.unmodifiableCollection(new ArrayList<>(users));
        }
    }

    @Override
    public Collection<User> getUsers(String filter) {
        Collection<User> users = getUsers();
        if (StringUtil.isNull(filter)) {
            return users;
        }

        ArrayList<User> match = new ArrayList<>();
        synchronized (this) {
            for (User user : users) {
                if (user.getName() != null && user.getName().contains(filter)) {
                    match.add(user);
                }
            }
        }
        return Collections.unmodifiableCollection(match);
    }

}
