package com.playtech.ptargame3.server.rank.calculator;

public enum ScoreCriteria {
	GOAL("goal", 0.5, 500), BALL_HIT("ballHit", 0.1, 10), BULLET_HIT("bulletHit", 0.2, 100), BOOST_HIT("boostHit", 0.2,
			20);

	private final String key;
	private final double value;
	private final long pointValue;

	ScoreCriteria(String key, double value, long pointValue) {
		this.key = key;
		this.value = value;
		this.pointValue = pointValue;
	}

	public String getKey() {
		return key;
	}

	public double getValue() {
		return value;
	}

	public long getPointValue() {
		return pointValue;
	}

}
