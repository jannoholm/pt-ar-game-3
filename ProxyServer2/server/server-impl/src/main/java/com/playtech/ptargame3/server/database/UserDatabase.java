package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.database.model.User;

import java.util.Collection;

public interface UserDatabase {

    void addUser(String name, String email);

    Collection<User> getUsers();

    Collection<User> getUsers(String filter);

}
