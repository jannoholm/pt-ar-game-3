package com.playtech.ptargame3.server.database;


public interface DatabaseAccess {

    UserDatabase getUserDatabase();

    GameDatabase getGameDatabase();

    RatingDatabase getRatingDatabase();

}
