package com.playtech.ptgame3.rank.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.playtech.ptargame3.server.rank.calculator.EloCalculatorImpl;
import com.playtech.ptargame3.server.rank.calculator.PlayerScore;
import com.playtech.ptargame3.server.rank.calculator.ScoreCriteria;

public class TestEloCalculator {

	static EloCalculatorImpl calculator;
	List<PlayerScore> team1;
	List<PlayerScore> team2;

	@BeforeClass
	public static void init() {
		calculator = new EloCalculatorImpl();
	}

	private Map<ScoreCriteria, Integer> generateMap(int goal, int ballHit, int boostHit, int bulletHit) {
		Map<ScoreCriteria, Integer> map = new HashMap<>();
		map.put(ScoreCriteria.GOAL, goal);
		map.put(ScoreCriteria.BALL_HIT, ballHit);
		map.put(ScoreCriteria.BOOST_HIT, boostHit);
		map.put(ScoreCriteria.BULLET_HIT, bulletHit);

		return map;
	}

	@Before
	public void executedBeforeEach() {
		PlayerScore p1 = new PlayerScore(10, 1000, generateMap(1, 0, 3, 4));
		PlayerScore p2 = new PlayerScore(11, 1000, generateMap(5, 6, 0, 8));
		team1 = Arrays.asList(p1, p2);

		PlayerScore p3 = new PlayerScore(20, 1000, generateMap(1, 2, 0, 2));
		PlayerScore p4 = new PlayerScore(21, 1000, generateMap(0, 1, 2, 1));
		team2 = Arrays.asList(p3, p4);

	}
	//
	// @Test
	// public void TestTeamEloCalculator() {
	// assertTrue(calculator.getTeamRating(team1) == 1000);
	// }

	@Test
	public void TestCalculatorWinLose() {
		calculator.calculatePlayerPoints(team1, team2);
		assertTrue(calculator.getTeamRating(team1) == 1008);
		assertTrue(calculator.getTeamRating(team2) == 992);
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		calculator.calculatePlayerPoints(team2, team1);

		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		calculator.calculatePlayerPoints(team2, team1);
		

		
		assertTrue(calculator.getTeamRating(team1) > calculator.getTeamRating(team2));
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		calculator.calculatePlayerPoints(team2, team1);
		assertTrue(calculator.getTeamRating(team1) > calculator.getTeamRating(team2));
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		calculator.calculatePlayerPoints(team2, team1);
		assertTrue(calculator.getTeamRating(team1) > calculator.getTeamRating(team2));
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		calculator.calculatePlayerPoints(team2, team1);
		assertTrue(calculator.getTeamRating(team1) > calculator.getTeamRating(team2));
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

		team2.get(1).setScoreMap(generateMap(7, 7, 7, 7));
		calculator.calculatePlayerPoints(team2, team1);
		
		assertTrue(calculator.getTeamRating(team1) > calculator.getTeamRating(team2));
		assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2) == 2000);

	}

	// @Test
	// public void TestCalculatorDraw() {
	// team1.get(1).setScoreMap(generateMap(4, 4, 4, 4));
	// team2.get(1).setScoreMap(generateMap(5, 5, 5, 5));
	//
	// calculator.calculatePlayerPoints(team1, team2);
	//
	// assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2)
	// == 2000);
	// assertTrue(calculator.getTeamRating(team1) ==
	// calculator.getTeamRating(team2));
	//
	// calculator.calculatePlayerPoints(team2, team1);
	//
	// assertTrue(calculator.getTeamRating(team1) + calculator.getTeamRating(team2)
	// == 2000);
	// assertTrue(calculator.getTeamRating(team1) ==
	// calculator.getTeamRating(team2));
	// }
	//
	// @Test
	// public void TestArbritartyScoreCalculator( ) {
	// assertTrue(calculator.calculatePlayerScore(generateMap(0, 0, 0, 0)) == 0);
	// assertTrue(calculator.calculatePlayerScore(generateMap(2, 2, 2, 2)) == 1260);
	// }

}
