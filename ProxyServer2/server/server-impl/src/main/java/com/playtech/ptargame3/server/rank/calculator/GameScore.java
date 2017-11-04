package com.playtech.ptargame3.server.rank.calculator;

public class GameScore {

	private long goalsSum;
	private long touchesSum;
	private long bulletHitsSum;
	private long boostTouchesSum;

	public GameScore(long goalsSum, long touchesSum, long bulletHitsSum, long boostTouchesSum) {
		super();
		this.goalsSum = goalsSum;
		this.touchesSum = touchesSum;
		this.bulletHitsSum = bulletHitsSum;
		this.boostTouchesSum = boostTouchesSum;
	}

	public long getGoalsSum() {
		return goalsSum;
	}

	public void setGoalsSum(long goalsSum) {
		this.goalsSum = goalsSum;
	}

	public long getTouchesSum() {
		return touchesSum;
	}

	public void setTouchesSum(long touchesSum) {
		this.touchesSum = touchesSum;
	}

	public long getBulletHitsSum() {
		return bulletHitsSum;
	}

	public void setBulletHitsSum(long bulletHitsSum) {
		this.bulletHitsSum = bulletHitsSum;
	}

	public long getBoostTouchesSum() {
		return boostTouchesSum;
	}

	public void setBoostTouchesSum(long boostTouchesSum) {
		this.boostTouchesSum = boostTouchesSum;
	}

}
