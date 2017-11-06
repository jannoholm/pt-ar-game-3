package com.playtech.ptargame3.server.rank.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EloCalculatorImpl implements ScoreCalculator {

	private int K = 32;
	private double minimumWeight = 0.25;

	public EloCalculatorImpl(int k, double minimumWeight) {
		super();
		K = k;
		this.minimumWeight = minimumWeight;
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

	/**
	 * Using this implementation:
	 * https://metinmediamath.wordpress.com/2013/11/27/how-to-calculate-the-elo-rating-including-example/
	 */
	@Override
	public void calculatePlayerPoints(List<PlayerScore> teamRed, List<PlayerScore> teamBlue) {

		long team1Elo = getTeamRating(teamRed);
		long team2Elo = getTeamRating(teamBlue);

		double team1R = calculateR(team1Elo);
		double team2R = calculateR(team2Elo);

		double team1E = calculateE(team1R, team2R);
		double team2E = calculateE(team2R, team1R);

		double gameResult = calculateGameResult(teamRed, teamBlue);

		List<Double> s = calculateS(gameResult);

		long newTeam1Elo = (long) (team1Elo + K * (s.get(0) - team1E));
		long newTeam2Elo = (long) (team2Elo + K * (s.get(1) - team2E));

		int team1Diff = (int) (newTeam1Elo - team1Elo);
		int team2Diff = (int) (newTeam2Elo - team2Elo);

		updateTeamScore(teamRed, team1Diff);
		updateTeamScore(teamBlue, team2Diff);
	}

	private double calculateGameResult(List<PlayerScore> teamRed, List<PlayerScore> teamBlue) {
		long redGoals = teamRed.stream().mapToLong(p -> p.getScoreMap().get(ScoreCriteria.BALL_HIT)).sum();
		long blueGoals = teamBlue.stream().mapToLong(p -> p.getScoreMap().get(ScoreCriteria.BALL_HIT)).sum();

		if (redGoals > blueGoals)
			return 1;
		if (blueGoals > redGoals)
			return 0;
		return 0.5;
	}

	private List<Double> calculateS(double gameResult) {
		if (gameResult == 1)
			return Arrays.asList(1.0, 0.0);
		if (gameResult == 0)
			return Arrays.asList(0.0, 1.0);
		return Arrays.asList(0.5, 0.5);
	}

	public long getTeamRating(List<PlayerScore> team) {
		return team.stream().mapToLong(PlayerScore::getElo).sum() / team.size();
	}

	private double calculateR(long elo) {
		return Math.pow(10, elo / 400.0);
	}

	private double calculateE(double r1, double r2) {
		return r1 / (r1 + r2);
	}

	private void updateTeamScore(List<PlayerScore> team, int eloDiff) {
		Map<ScoreCriteria, Integer> scoreMap = new HashMap<>();

		for (PlayerScore player : team) {
			Map<ScoreCriteria, Integer> playerScore = player.getScoreMap();
			for (Entry<ScoreCriteria, Integer> entry : playerScore.entrySet()) {
				if (scoreMap.containsKey(entry.getKey())) {
					int currentValue = scoreMap.get(entry.getKey());
					currentValue += playerScore.get(entry.getKey());
					scoreMap.put(entry.getKey(), currentValue);
				} else {
					scoreMap.put(entry.getKey(), playerScore.get(entry.getKey()));
				}

			}
		}

		GameScore score = new GameScore(scoreMap);

		int playerNumber = team.size();

		for (PlayerScore player : team) {
			double contribution = calculatePlayerContribution(player, score, playerNumber);
			contribution = eloDiff < 0 ? 1.0 - contribution : contribution;
			player.setUserScore(calculatePlayerScore(player.getScoreMap()));
			player.updateElo((int) Math.round(contribution * eloDiff));
		}

	}

	private double calculatePlayerContribution(PlayerScore player, GameScore totalScore, int playerNumber) {
		double ret = 0.0;

		Map<ScoreCriteria, Integer> totalMap = totalScore.getScoreMap();
		Map<ScoreCriteria, Integer> playerMap = player.getScoreMap();

		for (ScoreCriteria entry : totalMap.keySet())
			if (totalMap.get(entry) != 0 && entry.getValue() != 0) {
				ret += playerMap.get(entry) / totalMap.get(entry) * entry.getValue();
			}

		return ret * (1.0 - minimumWeight * playerNumber) + minimumWeight;
	}

	public int calculatePlayerScore(Map<ScoreCriteria, Integer> scoreMap) {
		int ret = 0;
		for (ScoreCriteria entry : scoreMap.keySet())
			ret += scoreMap.get(entry) * entry.getPointValue();
		return ret;
	}
}
