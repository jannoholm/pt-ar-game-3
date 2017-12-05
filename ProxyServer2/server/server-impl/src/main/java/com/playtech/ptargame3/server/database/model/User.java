package com.playtech.ptargame3.server.database.model;


public class User {

    public enum UserType {
        REGULAR,
        INTERNAL,
        BOT;

        public static UserType getUserType(int ut) {
            return values()[ut];
        }
    }

    private final int id;
    private final String name;
    private final String email;
    private final boolean hidden;
    private final UserType userType;

    public User(int id, String name, String email, boolean hidden, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.hidden = hidden;
        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hidden='" + hidden + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
