package com.playtech.ptargame3.server.task.table;

import com.playtech.ptargame3.api.lobby.Team;
import com.playtech.ptargame3.api.table.GameResultPlayerActivity;
import com.playtech.ptargame3.api.table.GameResultPlayerScore;
import com.playtech.ptargame3.api.table.GameResultStoreRequest;
import com.playtech.ptargame3.api.table.GameResultStoreResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.database.model.EloRating;
import com.playtech.ptargame3.server.database.model.Game;
import com.playtech.ptargame3.server.database.model.GamePlayerScore;
import com.playtech.ptargame3.server.rank.calculator.EloCalculatorImpl;
import com.playtech.ptargame3.server.rank.calculator.PlayerScore;
import com.playtech.ptargame3.server.rank.calculator.ScoreCriteria;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.util.TeamConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameResultStoreLogic extends AbstractLogic {
	public GameResultStoreLogic(LogicResources logicResources) {
		super(logicResources);
	}

	@Override
	public void execute(Task task) {
		GameResultStoreRequest request = getInputRequest(task, GameResultStoreRequest.class);

		// convert data to feed calculator
		ArrayList<PlayerScore> teamRed = new ArrayList<>();
		ArrayList<PlayerScore> teamBlue = new ArrayList<>();
		ArrayList<Holder> allScores = new ArrayList<>();
		for (GameResultPlayerActivity requestPlayer : request.getPlayerResults()) {
			Map<ScoreCriteria, Integer> scoreMap = new HashMap<>();
			scoreMap.put(ScoreCriteria.GOAL, requestPlayer.getGoals());
			scoreMap.put(ScoreCriteria.BALL_HIT, requestPlayer.getBallTouches());
			scoreMap.put(ScoreCriteria.BULLET_HIT, requestPlayer.getBulletHits());
			scoreMap.put(ScoreCriteria.BOOST_HIT, requestPlayer.getBoostTouches());

			EloRating rating = getLogicResources().getDatabaseAccess().getRatingDatabase()
					.getRating(requestPlayer.getUserId());

			PlayerScore ps = new PlayerScore(requestPlayer.getUserId(), rating.getEloRating(), scoreMap);
			allScores.add(new Holder(requestPlayer, ps, rating));
		}

		// do calculations
		EloCalculatorImpl calculator = new EloCalculatorImpl();
		calculator.calculatePlayerPoints(teamRed, teamBlue);

		// update elo rating
		updateEloRating(request, allScores);

		// calculate leaderboard position
		calculateLeaderboardPosition(allScores);

		// write game history
		writeGameHistory(request, allScores);

		// create response
		GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class);
		for (Holder holder : allScores) {
			GameResultPlayerScore score = new GameResultPlayerScore();
			score.setTeam(holder.getRequestData().getTeam());
			score.setPositionInTeam(holder.getRequestData().getPositionInTeam());
			score.setScore(holder.getScore().getUserScore());
			score.setEloRating(holder.getRating().getEloRating());
			score.setLeaderboardPosition(holder.leaderboardPosition);
			response.addPlayerResult(score);
		}
		task.getContext().put(ContextConstants.RESPONSE, response);
	}

	@Override
	public void finishSuccess(Task task) {
		super.finishSuccess(task);
		GameResultStoreResponse response = task.getContext().get(ContextConstants.RESPONSE,
				GameResultStoreResponse.class);
		getLogicResources().getCallbackHandler().sendMessage(response);
	}

	@Override
	public void finishError(Task task, Exception e) {
		super.finishError(task, e);
		GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class, e);
		getLogicResources().getCallbackHandler().sendMessage(response);
	}

	private void writeGameHistory(GameResultStoreRequest request, ArrayList<Holder> allScores) {
		int goalsRed = 0;
		int goalsBlue = 0;
		ArrayList<GamePlayerScore> playerResults = new ArrayList<>();
		for (Holder holder : allScores) {
			if (holder.getRequestData().getTeam() == Team.RED) {
				goalsRed += holder.getRequestData().getGoals();
			} else if (holder.getRequestData().getTeam() == Team.BLUE) {
				goalsBlue += holder.getRequestData().getGoals();
			}
			GamePlayerScore playerScore = new GamePlayerScore(holder.getRequestData().getUserId(),
					TeamConverter.convert(holder.getRequestData().getTeam()), holder.getRequestData().getGoals(),
					holder.getRequestData().getBulletHits(), holder.getRequestData().getBallTouches(),
					holder.getRequestData().getBoostTouches(), holder.getScore().getUserScore(),
					holder.getRating().getEloRating(), holder.leaderboardPosition);
			playerResults.add(playerScore);
		}
		int round = getLogicResources().getGameRegistry().getGame(request.getGameId()).updateRound();
		Game game = new Game(request.getGameId() + "+" + round, goalsRed, goalsBlue, request.isSuddenDeath(),
				request.getGameTime(), playerResults);
		getLogicResources().getDatabaseAccess().getGameDatabase().addGame(game);
	}

	private void calculateLeaderboardPosition(ArrayList<Holder> allScores) {
		Collection<EloRating> leaderboard = getLogicResources().getDatabaseAccess().getRatingDatabase()
				.getLeaderboard();
		int pos = 0;
		for (EloRating lb : leaderboard) {
			pos++;
			for (Holder holder : allScores) {
				if (lb.getUserId() == holder.getRequestData().getUserId()) {
					holder.leaderboardPosition = pos;
				}
			}
		}
	}

	private void updateEloRating(GameResultStoreRequest request, ArrayList<Holder> allScores) {
		for (Holder holder : allScores) {
			holder.rating = new EloRating(holder.getRequestData().getUserId(), holder.getScore().getElo(),
					holder.getRating().getMatches() + 1,
					holder.getRating().getGoals() + holder.getRequestData().getGoals(),
					holder.getRating().getBulletHits() + holder.getRequestData().getBulletHits(),
					holder.getRating().getTotalScore() + holder.getScore().getUserScore(),
					holder.getRating().getBallTouches() + holder.getRequestData().getBallTouches(),
					holder.getRating().getBoostTouches() + holder.getRequestData().getBoostTouches(),
					holder.getRating().getBoostTouches()
							+ (isWinner(request.getWinnerTeam(), holder.getRequestData().getTeam()) ? 1 : 0));
			getLogicResources().getDatabaseAccess().getRatingDatabase().updateRating(holder.getRating());
		}
	}

	private boolean isWinner(GameResultStoreRequest.WinnerTeam winnerTeam, Team playerTeam) {
		if (winnerTeam == GameResultStoreRequest.WinnerTeam.RED && playerTeam == Team.RED) {
			return true;
		} else if (winnerTeam == GameResultStoreRequest.WinnerTeam.BLUE && playerTeam == Team.BLUE) {
			return true;
		}
		return false;
	}

	private static class Holder {
		private GameResultPlayerActivity requestData;
		private PlayerScore score;
		private EloRating rating;
		private int leaderboardPosition = -1;

		public Holder(GameResultPlayerActivity requestData, PlayerScore score, EloRating rating) {
			this.requestData = requestData;
			this.score = score;
			this.rating = rating;
		}

		public PlayerScore getScore() {
			return score;
		}

		public GameResultPlayerActivity getRequestData() {
			return requestData;
		}

		public EloRating getRating() {
			return rating;
		}
	}

}
