var buffer = argument[0];

var error_code = buffer_read(buffer, buffer_s32);
var error_message = buffer_read(buffer, buffer_string);
show_debug_message("got game result response: " + string(error_code) + ":" + error_message);

if (error_code != 0) {
	return;
}

buffer_read(buffer, buffer_s32); // it is always 4 for now

for (var i=0; i < 4; ++i) {
	buffer_read(buffer, buffer_s32); // ignore count for now
	var teamColor=buffer_read(buffer, buffer_s8);
	var teamPos=buffer_read(buffer, buffer_s8);
	var gamescore=buffer_read(buffer, buffer_s32);
	var eloRating=buffer_read(buffer, buffer_s32);
	var leaderboardPosition=buffer_read(buffer, buffer_s32);
	var eloRatingDiff=buffer_read(buffer, buffer_s32);
	var leaderboardPositionDiff=buffer_read(buffer, buffer_s32);
	show_debug_message("car: " + string(teamColor) + ":" + string(teamPos) + " score is " + string(gamescore));
	
	if (eloRatingDiff >= 0) {
		eloRatingDiff="(+"+string(eloRatingDiff)+")";
	} else if (eloRatingDiff < 0) {
		eloRatingDiff="("+string(eloRatingDiff)+")";
	}
	if (leaderboardPositionDiff >= 0) {
		leaderboardPositionDiff="(+"+string(leaderboardPositionDiff)+")";
	} else if (leaderboardPositionDiff < 0) {
		leaderboardPositionDiff="("+string(leaderboardPositionDiff)+")";
	}
	
	if (teamColor == 0 && teamPos == 1) {
		with (obj_playerinit_physics.red1) {
			score_gamescore = gamescore;
			score_eloRating = eloRating;
			score_leaderboardPosition = leaderboardPosition;
			score_eloRatingDiff = eloRatingDiff;
			score_leaderboardPositionDiff = leaderboardPositionDiff;
			show_debug_message("red1 score: " + string(score_gamescore));
		}
	} else if (teamColor == 0 && teamPos == 2) {
		with (obj_playerinit_physics.red2) {
			score_gamescore = gamescore;
			score_eloRating = eloRating;
			score_leaderboardPosition = leaderboardPosition;
			score_eloRatingDiff = eloRatingDiff;
			score_leaderboardPositionDiff = leaderboardPositionDiff;
			show_debug_message("red1 score: " + string(score_gamescore));
		}
	} else if (teamColor == 1 && teamPos == 1) {
		with (obj_playerinit_physics.blue1) {
			score_gamescore = gamescore;
			score_eloRating = eloRating;
			score_leaderboardPosition = leaderboardPosition;
			score_eloRatingDiff = eloRatingDiff;
			score_leaderboardPositionDiff = leaderboardPositionDiff;
			show_debug_message("red1 score: " + string(score_gamescore));
		}
	} else if (teamColor == 1 && teamPos == 2) {
		with (obj_playerinit_physics.blue2) {
			score_gamescore = gamescore;
			score_eloRating = eloRating;
			score_leaderboardPosition = leaderboardPosition;
			score_eloRatingDiff = eloRatingDiff;
			score_leaderboardPositionDiff = leaderboardPositionDiff;
			show_debug_message("red1 score: " + string(score_gamescore));
		}
	}
}


