package com.playtech.ptargame3.server.rank.calculator;

import java.util.Map;

public class GameScore {

	private Map<ScoreCriteria, Integer> scoreMap;

	public GameScore(Map<ScoreCriteria, Integer> scoreMap) {
		super();
		this.scoreMap = scoreMap;
	}

	public Map<ScoreCriteria, Integer> getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(Map<ScoreCriteria, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}

}
