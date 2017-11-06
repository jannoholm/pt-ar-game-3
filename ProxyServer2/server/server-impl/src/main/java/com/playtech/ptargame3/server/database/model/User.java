package com.playtech.ptargame3.server.database.model;


public class User {

    private final int id;
    private final String name;
    private final String email;
    private final boolean hidden;
    private final boolean internal;

    public User(int id, String name, String email, boolean hidden, boolean internal) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hidden = hidden;
        this.internal = internal;
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

    public boolean isInternal() {
        return internal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hidden='" + hidden + '\'' +
                ", internal='" + internal + '\'' +
                '}';
    }
}
