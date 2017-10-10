if (newPhase == GamePhaseRename.GAME_END_ANIMATION) {
	draw_set_font(fnt_gamewin);
	draw_set_color(c_white);

	var wintext="";
	if (teamRedScore < teamBlueScore) {
		wintext="BLUE WINS";
	} else if (teamRedScore > teamBlueScore) {
		wintext="RED WINS";
	} else {
		wintext="Game is a draw";
	}
	draw_text(1920/3, 1080/2-200, "GAME OVER");
	draw_text(1920/3, 1080/2, wintext);
	draw_text(1920/3, 1080/2+200, "Score: " + string(teamRedScore) + " vs. " + string(teamBlueScore));
} else if (newPhase == GamePhaseRename.PLAY) {
	draw_set_font(fnt_gamewin);
	draw_set_color(c_white);
	draw_text(1800, 40, string(floor(game_timer/room_speed+1)));
}