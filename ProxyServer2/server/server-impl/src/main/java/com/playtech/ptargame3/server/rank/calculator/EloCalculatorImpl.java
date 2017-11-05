package com.playtech.ptargame3.server.rank.calculator;

import java.util.List;

public class EloCalculatorImpl implements ScoreCalculator {
	
	private static final double goalWeight = 0.5;
	private static final double bulletHit = 0.2;
	private static final double ballTouch = 0.2;
	private static final double boostHit = 0.1;

	private int K = 32;

	public EloCalculatorImpl(int k) {
		super();
		K = k;
	}

	public EloCalculatorImpl() {
		super();
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	/** Using this implementation:
	 * https://metinmediamath.wordpress.com/2013/11/27/how-to-calculate-the-elo-rating-including-example/
	 */
	@Override
	public void calculatePlayerPoints(List<PlayerScore> teamRed, List<PlayerScore> teamBlue, double gameResult) {
		
		long team1Elo = getTeamRating(teamRed);
		long team2Elo = getTeamRating(teamBlue);
		
		double team1R = calculateR(team1Elo);
		double team2R = calculateR(team2Elo);
		
		double team1E = calculateE(team1R, team2R);
		double team2E = calculateE(team2R, team1R);
		
		long newTeam1Elo = (long) (team1Elo + K * (gameResult - team1E));
		long newTeam2Elo = (long) (team2Elo + K * (gameResult - team2E));
		
		int team1Diff = (int)(newTeam1Elo - team1Elo);
        int team2Diff = (int)(newTeam2Elo - team2Elo);
		
		updateTeamScore(teamRed, team1Diff);
		updateTeamScore(teamBlue, team2Diff);
	}
	
	private long getTeamRating(List<PlayerScore> team) {
		return team.stream().mapToLong(PlayerScore::getElo).sum() / team.size();
	}
	
	private double calculateR(long elo) {
		return Math.pow(10, elo/ 400.0);
	}
	
	private double calculateE(double r1, double r2) {
		return r1 / (r1 + r2);
	}

	private void updateTeamScore(List<PlayerScore> team, int eloDiff) {
		int goalsSum = 0;
		int touchesSum = 0;
		int bulletHitsSum = 0;
		int boostTouchesSum = 0;
		
		for(PlayerScore player : team) {
			goalsSum += player.getGoals();
			touchesSum += player.getTouches();
			bulletHitsSum += player.getBulletHits();
			boostTouchesSum += player.getBoostTouches();
		}
		
		GameScore score = new GameScore(goalsSum, touchesSum, bulletHitsSum, boostTouchesSum);

		for(PlayerScore player : team) {
            player.setScore((int) Math.round(calculatePlayerContribution(team.get(0), score)));
			player.updateElo(player.getScore()*eloDiff);
		}
		
	}
	
	private double calculatePlayerContribution(PlayerScore player, GameScore totalScore) {
		double ret = 0;
		if (player.getGoals() > 0) {
            ret += totalScore.getGoalsSum() / player.getGoals() * goalWeight;
        }
        if (player.getTouches() > 0) {
            ret += totalScore.getTouchesSum() / player.getTouches() * ballTouch;
        }
        if (player.getBoostTouches() > 0) {
            ret += totalScore.getBoostTouchesSum() / player.getBoostTouches() * boostHit;
        }
        if (player.getBulletHits() > 0) {
            ret += totalScore.getBulletHitsSum() / player.getBulletHits() * bulletHit;
        }
		return ret;
	}
}
