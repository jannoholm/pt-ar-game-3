package com.playtech.ptargame3.server.rank.calculator;

public class PlayerScore {

	private String playerId;

	private long goals;

	private long touches;

	private long bulletHits;

	private long boostTouches;

	private long elo;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public long getGoals() {
		return goals;
	}

	public void setGoals(long goals) {
		this.goals = goals;
	}

	public long getTouches() {
		return touches;
	}

	public void setTouches(long touches) {
		this.touches = touches;
	}

	public long getBulletHits() {
		return bulletHits;
	}

	public void setBulletHits(long bulletHits) {
		this.bulletHits = bulletHits;
	}

	public long getBoostTouches() {
		return boostTouches;
	}

	public void setBoostTouches(long boostTouches) {
		this.boostTouches = boostTouches;
	}

	public long getElo() {
		return elo;
	}

	public void setElo(long elo) {
		this.elo = elo;
	}
	
	public void updateElo(long diff) {
		this.elo += diff;
	}

}
