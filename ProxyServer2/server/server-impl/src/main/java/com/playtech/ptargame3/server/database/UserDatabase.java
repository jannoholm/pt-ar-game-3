package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.database.model.User;

import java.util.Collection;

public interface UserDatabase {

    User addUser(String name, String email);

    User getUser(int id);

    void updateUser(User user);

    Collection<User> getUsers();

    Collection<User> getUsers(String filter);

}
