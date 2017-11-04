package com.playtech.ptargame3.server.rank.calculator;

import java.util.List;
import java.util.Map;

public class EloCalculatorImpl implements ScoreCalculator {
	
	private double goalWeight = 0.5;
	private double bulletHit = 0.2;
	private double ballTouch = 0.2;
	private double boostHit = 0.1;

	private int K = 32;

	public EloCalculatorImpl(int k, Map<String, Long> scoreSplits) {
		super();
		K = k;
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	@Override
	public void calculatePlayerPoints(List<PlayerScore> team1, List<PlayerScore> team2, double gameResult) {
		
		long team1Elo = getTeamRating(team1);
		long team2Elo = getTeamRating(team2);
		
		double team1R = calulateR(team1Elo);
		double team2R = calulateR(team2Elo);
		
		double team1E = calculateE(team1R, team2R);
		double team2E = calculateE(team2R, team1R);
		
		long newTeam1Elo = (long) (team1Elo + K * (gameResult - team1E));
		long newTeam2Elo = (long) (team2Elo + K * (gameResult - team2E));
		
		long team1Diff = newTeam1Elo - team1Elo;
		long team2Diff = newTeam2Elo - team2Elo;
		
		updateTeamScore(team1, team1Diff);
		updateTeamScore(team2, team2Diff);	
	}
	
	private long getTeamRating(List<PlayerScore> team) {
		return team.stream().mapToLong(player -> player.getElo()).sum() / team.size();
	}
	
	private double calulateR(long elo) {
		return Math.pow(10, elo/ 400.0);
	}
	
	private double calculateE(double r1, double r2) {
		return r1 / (r1 + r2);
	}

	private void updateTeamScore(List<PlayerScore> team, long eloDiff) {
		long goalsSum = 0;
		long touchesSum = 0;
		long bulletHitsSum = 0;
		long boostTouchesSum = 0;
		
		for(PlayerScore player : team) {
			goalsSum += player.getGoals();
			touchesSum += player.getTouches();
			bulletHitsSum += player.getBulletHits();
			boostTouchesSum += player.getBoostTouches();
		}
		
		GameScore score = new GameScore(goalsSum, touchesSum, bulletHitsSum, boostTouchesSum);

		for(PlayerScore player : team) {
			player.updateElo(Math.round(calculatePlayerContribution(team.get(0), score) * eloDiff));
		}
		
	}
	
	private double calculatePlayerContribution(PlayerScore player, GameScore totalScore) {
		double ret = 0;
		ret += totalScore.getGoalsSum() / player.getGoals() * goalWeight;
		ret += totalScore.getTouchesSum() / player.getTouches() * ballTouch;
		ret += totalScore.getBoostTouchesSum() / player.getBoostTouches() * boostHit;
		ret += totalScore.getBulletHitsSum() / player.getBulletHits() * bulletHit;
		return ret;
	}
}
