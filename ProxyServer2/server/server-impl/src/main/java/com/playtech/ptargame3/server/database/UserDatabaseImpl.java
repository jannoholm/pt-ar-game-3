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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDatabaseImpl implements UserDatabase {

    private static final Logger logger = Logger.getLogger(UserDatabaseImpl.class.getName());

    private static final String TABLE_USERS = "USERS";
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final DatabaseAccessImpl dbInit;
    private final ScheduledExecutorService executor;
    private ScheduledFuture maintenanceFuture;

    private ArrayList<User> pendingWrites = new ArrayList<>();
    private Map<Integer, User> userMap = new HashMap<>();

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

            // check if hidden field already exists
            boolean alterNeeded = true;
            sql = "PRAGMA table_info( " + TABLE_USERS + " )";
            try (ResultSet result = stmt.executeQuery(sql)) {
                while (result.next()) {
                    if (result.getString("NAME").equals("HIDDEN")) {
                        alterNeeded = false;
                        break;
                    }
                }
            }

            // add hidden field. needed for backward compatibility
            if (alterNeeded) {
                sql = "ALTER TABLE " + TABLE_USERS + " ADD COLUMN HIDDEN INT NOT NULL DEFAULT 0";
                stmt.executeUpdate(sql);
            }

            // check if hidden field already exists
            alterNeeded = true;
            sql = "PRAGMA table_info( " + TABLE_USERS + " )";
            try (ResultSet result = stmt.executeQuery(sql)) {
                while (result.next()) {
                    if (result.getString("NAME").equals("INTERNAL")) {
                        alterNeeded = false;
                        break;
                    }
                }
            }

            // add hidden field. needed for backward compatibility
            if (alterNeeded) {
                sql = "ALTER TABLE " + TABLE_USERS + " ADD COLUMN INTERNAL INT NOT NULL DEFAULT 0";
                stmt.executeUpdate(sql);
            }

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
            String sql = "select ID, NAME, EMAIL, HIDDEN, INTERNAL from " + TABLE_USERS + " order by id";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("NAME");
                String email = result.getString("EMAIL");
                int hidden = result.getInt("HIDDEN");
                int internal = result.getInt("INTERNAL");
                User user = new User(id, name.toUpperCase(), email, hidden > 0, User.UserType.getUserType(internal));
                userMap.put(user.getId(), user);
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
                    String selectSql = "select count(1) from " + TABLE_USERS + " where ID=?";
                    String insertSql = "insert into " + TABLE_USERS + " (ID, NAME, EMAIL, HIDDEN, INTERNAL) values (?, ?, ?, ?, ?)";
                    String updateSql = "update " + TABLE_USERS + " set NAME=?, EMAIL=?, HIDDEN=?, INTERNAL=? where ID=?";
                    Connection connection = dbInit.allocateConnection();
                    try (
                            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
                            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                    ) {
                        for (User user : todo) {
                            selectStmt.setInt(1, user.getId());
                            ResultSet result = selectStmt.executeQuery();
                            result.next();
                            if (result.getInt(1) > 0) {
                                // update
                                updateStmt.setString(1, user.getName());
                                updateStmt.setString(2, user.getEmail());
                                updateStmt.setInt(3, user.isHidden() ? 1 : 0);
                                updateStmt.setInt(4, user.getUserType().ordinal());
                                updateStmt.setInt(5, user.getId());
                                updateStmt.executeUpdate();
                            } else {
                                // insert
                                insertStmt.setInt(1, user.getId());
                                insertStmt.setString(2, user.getName());
                                insertStmt.setString(3, user.getEmail());
                                insertStmt.setInt(4, user.isHidden() ? 1 : 0);
                                insertStmt.setInt(5, user.getUserType().ordinal());
                                insertStmt.executeUpdate();
                            }
                            logger.info("User written to database: " + user);
                            synchronized (this) {
                                pendingWrites.remove(user);
                            }
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Unable to initialize users database", e);
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

    public User addUser(String name, String email, User.UserType userType) {
        if (StringUtil.isNull(name)) throw new NullPointerException("Name cannot be null.");
        User user = new User(idGenerator.incrementAndGet(), name.toUpperCase().trim(), email, false, userType);
        synchronized (this) {
            pendingWrites.add(user);
            userMap.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User getUser(int id) {
        synchronized (this) {
            return userMap.get(id);
        }
    }

    @Override
    public void updateUser(User user) {
        if (StringUtil.isNull(user.getName())) throw new NullPointerException("Name cannot be null.");
        synchronized (this) {
            User existing = userMap.get(user.getId());
            if (existing != null) {
                user = new User(user.getId(), user.getName().toUpperCase(), user.getEmail(), user.isHidden(), user.getUserType());
                userMap.put(user.getId(), user);
                pendingWrites.add(user);
                logger.info("Adding to pending writes");
            } else {
                logger.info("player not found: " + user);
            }
        }
    }

    public Collection<User> getUsers() {
        synchronized (this) {
            return Collections.unmodifiableCollection(new ArrayList<>(userMap.values()));
        }
    }

    @Override
    public Collection<User> getUsers(String filter) {
        Collection<User> users = getUsers();
        if (StringUtil.isNull(filter)) {
            return users;
        }

        filter = filter.toUpperCase();
        ArrayList<User> match = new ArrayList<>();
        for (User user : users) {
            if (user.getName().contains(filter)) {
                match.add(user);
            }
        }
        return Collections.unmodifiableCollection(match);
    }

    @Override
    public Collection<User> getUsersByName(String name) {
        if (StringUtil.isNull(name)) throw new NullPointerException("Name cannot be null.");
        name = name.toUpperCase();
        ArrayList<User> match = new ArrayList<>();
        for (User user : getUsers()) {
            if (user.getName().equals(name)) {
                match.add(user);
            }
        }
        return Collections.unmodifiableCollection(match);
    }

}
