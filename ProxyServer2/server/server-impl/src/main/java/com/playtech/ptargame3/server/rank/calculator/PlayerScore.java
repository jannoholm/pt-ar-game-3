package com.playtech.ptargame3.server.rank.calculator;

import com.playtech.ptargame3.server.database.model.EloRating;

public class PlayerScore {

	private int userId;
	private int goals;
	private int touches;
	private int bulletHits;
	private int boostTouches;
    private int score;
	private int elo;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getTouches() {
		return touches;
	}

	public void setTouches(int touches) {
		this.touches = touches;
	}

	public int getBulletHits() {
		return bulletHits;
	}

	public void setBulletHits(int bulletHits) {
		this.bulletHits = bulletHits;
	}

	public int getBoostTouches() {
		return boostTouches;
	}

	public void setBoostTouches(int boostTouches) {
		this.boostTouches = boostTouches;
	}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getElo() {
		return elo;
	}

	public void updateElo(int diff) {
		this.elo += diff;
	}

}
