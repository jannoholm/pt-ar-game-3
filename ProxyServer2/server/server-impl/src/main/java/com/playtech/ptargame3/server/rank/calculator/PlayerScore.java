package com.playtech.ptargame3.server.rank.calculator;

import java.util.Map;

public class PlayerScore {

	private int userId;
	private int elo;
	private int userScore;

	private Map<ScoreCriteria, Integer> scoreMap;

	public PlayerScore(int userId, int elo, Map<ScoreCriteria, Integer> scoreMap) {
		super();
		this.userId = userId;
		this.elo = elo;
		this.scoreMap = scoreMap;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Map<ScoreCriteria, Integer> getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(Map<ScoreCriteria, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	@Override
	public String toString() {
		return "PlayerScore [userId=" + userId + ", elo=" + elo + ", userScore=" + userScore + ", scoreMap=" + scoreMap
				+ "]";
	}

}
