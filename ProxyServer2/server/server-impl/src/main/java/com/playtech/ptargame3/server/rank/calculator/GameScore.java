package com.playtech.ptargame3.server.rank.calculator;

public class GameScore {

	private int goalsSum;
	private int touchesSum;
	private int bulletHitsSum;
	private int boostTouchesSum;

	public GameScore(int goalsSum, int touchesSum, int bulletHitsSum, int boostTouchesSum) {
		super();
		this.goalsSum = goalsSum;
		this.touchesSum = touchesSum;
		this.bulletHitsSum = bulletHitsSum;
		this.boostTouchesSum = boostTouchesSum;
	}

	public int getGoalsSum() {
		return goalsSum;
	}

	public void setGoalsSum(int goalsSum) {
		this.goalsSum = goalsSum;
	}

	public int getTouchesSum() {
		return touchesSum;
	}

	public void setTouchesSum(int touchesSum) {
		this.touchesSum = touchesSum;
	}

	public int getBulletHitsSum() {
		return bulletHitsSum;
	}

	public void setBulletHitsSum(int bulletHitsSum) {
		this.bulletHitsSum = bulletHitsSum;
	}

	public int getBoostTouchesSum() {
		return boostTouchesSum;
	}

	public void setBoostTouchesSum(int boostTouchesSum) {
		this.boostTouchesSum = boostTouchesSum;
	}

}
