package com.playtech.ptargame3.server.database.model;

import com.playtech.ptargame3.server.registry.GameRegistryGame;

public class GamePlayerScore {

    private final int userId;
    private final GameRegistryGame.Team team;
    private final int goals;
    private final int bulletHits;
    private final int ballTouches;
    private final int boostTouches;
    private final int score;
    private final int eloRating;
    private final int leaderboardPos;

    public GamePlayerScore(int userId, GameRegistryGame.Team team,
                           int goals, int bulletHits, int ballTouches, int boostTouches,
                           int score, int eloRating, int leaderboardPos) {
        this.userId = userId;
        this.team = team;
        this.goals = goals;
        this.bulletHits = bulletHits;
        this.ballTouches = ballTouches;
        this.boostTouches = boostTouches;
        this.score = score;
        this.eloRating = eloRating;
        this.leaderboardPos = leaderboardPos;
    }

    public int getUserId() {
        return userId;
    }

    public GameRegistryGame.Team getTeam() {
        return team;
    }

    public int getGoals() {
        return goals;
    }

    public int getBulletHits() {
        return bulletHits;
    }

    public int getBallTouches() {
        return ballTouches;
    }

    public int getBoostTouches() {
        return boostTouches;
    }

    public int getScore() {
        return score;
    }

    public int getEloRating() {
        return eloRating;
    }

    public int getLeaderboardPos() {
        return leaderboardPos;
    }

    @Override
    public String toString() {
        return "GamePlayerScore{" +
                "userId=" + userId +
                ", team=" + team +
                ", goals=" + goals +
                ", bulletHits=" + bulletHits +
                ", ballTouches=" + ballTouches +
                ", boostTouches=" + boostTouches +
                ", score=" + score +
                ", eloRating=" + eloRating +
                ", leaderboardPos=" + leaderboardPos +
                '}';
    }
}
