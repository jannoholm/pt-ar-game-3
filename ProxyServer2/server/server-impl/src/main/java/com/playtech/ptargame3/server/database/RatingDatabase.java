package com.playtech.ptargame3.server.database;


import com.playtech.ptargame3.server.database.model.EloRating;

import java.util.Collection;

public interface RatingDatabase {

    EloRating getRating(int userId);

    void updateRating(EloRating rating);

    Collection<EloRating> getLeaderboard();

}
