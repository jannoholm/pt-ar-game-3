package com.playtech.ptargame3.server.database.model;


public class User {

    private final int id;
    private final String name;
    private final String email;
    private final boolean hidden;

    public User(int id, String name, String email, boolean hidden) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hidden = hidden;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hidden='" + hidden + '\'' +
                '}';
    }
}
