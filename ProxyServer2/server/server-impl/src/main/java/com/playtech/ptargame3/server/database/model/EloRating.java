package com.playtech.ptargame3.server.database.model;


public class EloRating {

    private final int userId;
    private final int eloRating;
    private final int matches;
    private final int goals;
    private final int bulletHits;
    private final int totalScore;
    private final int ballTouches;
    private final int boostTouches;
    private final int wins;

    public EloRating(int userId, int eloRating, int matches, int goals, int bulletHits,
                     int totalScore, int ballTouches, int boostTouches, int wins) {
        this.userId = userId;
        this.eloRating = eloRating;
        this.matches = matches;
        this.goals = goals;
        this.bulletHits = bulletHits;
        this.totalScore = totalScore;
        this.ballTouches = ballTouches;
        this.boostTouches = boostTouches;
        this.wins = wins;
    }

    public int getUserId() {
        return userId;
    }

    public int getEloRating() {
        return eloRating;
    }

    public int getMatches() {
        return matches;
    }

    public int getGoals() {
        return goals;
    }

    public int getBulletHits() {
        return bulletHits;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getBallTouches() {
        return ballTouches;
    }

    public int getBoostTouches() {
        return boostTouches;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return "EloRating{" +
                "userId=" + userId +
                ", eloRating=" + eloRating +
                ", matches=" + matches +
                ", goals=" + goals +
                ", bulletHits=" + bulletHits +
                ", totalScore=" + totalScore +
                ", ballTouches=" + ballTouches +
                ", boostTouches=" + boostTouches +
                ", wins=" + wins +
                '}';
    }
}
