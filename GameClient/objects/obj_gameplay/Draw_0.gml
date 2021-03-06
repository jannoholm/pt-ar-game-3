draw_set_font(fnt_gamewin);
draw_set_color(c_white);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

if (currentGamePhase == GamePhase.COUNTDOWN_TO_START) {
	//draw_text(1920/2, 1080/2, string(floor(game_timer/room_speed)+1));
} else if (currentGamePhase == GamePhase.GAME_END_ANIMATION) {
	var wintext="";
	if (teamRedScore < teamBlueScore) {
		wintext="BLUE WINS";
	} else if (teamRedScore > teamBlueScore) {
		wintext="RED WINS";
	} else {
		wintext="GAME IS A DRAW";
	}
	
	draw_text_color(1920/2, 1080/2-150, "GAME OVER", c_gray, c_gray, c_gray, c_gray, 0.5);
	draw_text_color(1920/2, 1080/2, wintext, c_gray, c_gray, c_gray, c_gray, 0.5);
	draw_text_color(1920/2, 1080/2+150, "SCORE: " + string(teamRedScore) + " VS. " + string(teamBlueScore), c_gray, c_gray, c_gray, c_gray, 0.5);

	draw_text_color(1920/2-1, 1080/2-150-1, "GAME OVER", c_orange, c_orange, c_orange, c_orange, 0.5);
	draw_text_color(1920/2-1, 1080/2-1, wintext, c_orange, c_orange, c_orange, c_orange, 0.5);
	draw_text_color(1920/2-1, 1080/2+150-1, "SCORE: " + string(teamRedScore) + " VS. " + string(teamBlueScore), c_orange, c_orange, c_orange, c_orange, 0.5);
} else if (currentGamePhase == GamePhase.PLAY || currentGamePhase == GamePhase.SUDDEN_DEATH) {
	/*if (currentCarPhase == CarPhase.COUNTDOWN_TO_START) {
		draw_set_halign(fa_center);
        draw_text(1920/2, 1080/2, string(floor(move_to_position_timer/room_speed)+1));
	}*/
	draw_set_font(fnt_score);
	draw_set_halign(fa_center);

	//draw_text_ext_color(1920/4,1080/3,string(teamRedScore),0,500,c_orange,c_orange,c_orange,c_orange,0.2);
	//draw_text_ext_color(1920/4+1920/2,1080/3,string(teamBlueScore),0,500,c_orange,c_orange,c_orange,c_orange,0.2);
	
	draw_text_ext_transformed_color(1920/4,1080/3,string(teamRedScore), 0, 500, 1.8, 1.8, 0, c_orange, c_orange, c_orange, c_orange, 0.3)
	draw_text_ext_transformed_color(1920/4+1920/2,1080/3,string(teamBlueScore), 0, 500, 1.8, 1.8, 0, c_orange, c_orange, c_orange, c_orange, 0.3)
}

if (currentGamePhase == GamePhase.WAIT_TO_START || currentGamePhase == GamePhase.COUNTDOWN_TO_START) {
	draw_sprite_ext(spr_gamepad, 1, 1920/4+100, 1080/2, 0.5, 0.5, -90, c_white, 0.5);
	draw_sprite_ext(spr_gamepad, 1, 1920/4+1920/2-100, 1080/2, 0.5, 0.5, 90, c_white, 0.5);
}
