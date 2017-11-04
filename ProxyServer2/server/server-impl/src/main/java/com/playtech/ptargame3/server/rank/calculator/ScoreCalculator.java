package com.playtech.ptargame3.server.rank.calculator;

import java.util.List;

public interface ScoreCalculator {
	
	//Game results are as following
	//0 = team 1 won
	//0.5 = tie
	//1 = team 2 won
	public void calculatePlayerPoints(List<PlayerScore> team1, List<PlayerScore> team2, double gameResult);

}
