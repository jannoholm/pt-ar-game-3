draw_set_font(fnt_gamewin);
draw_set_color(c_white);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

if (currentGamePhase == GamePhase.COUNTDOWN_TO_START) {
	draw_text(1920/2, 1080/2, string(floor(game_timer/room_speed)+1));
} else if (currentGamePhase == GamePhase.GAME_END_ANIMATION) {
	var wintext="";
	if (teamRedScore < teamBlueScore) {
		wintext="BLUE WINS";
	} else if (teamRedScore > teamBlueScore) {
		wintext="RED WINS";
	} else {
		wintext="Game is a draw";
	}
	draw_text(1920/2, 1080/2-150, "GAME OVER");
	draw_text(1920/2, 1080/2, wintext);
	draw_text(1920/2, 1080/2+150, "Score: " + string(teamRedScore) + " vs. " + string(teamBlueScore));
} else if (currentGamePhase == GamePhase.PLAY) {
	draw_set_halign(fa_right);
	draw_text(1850, 60, string(floor(game_timer/room_speed+1)));
}