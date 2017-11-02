var show = false;
var pos = 0;

if (g.currentGamePhase == GamePhase.PLAY || g.currentGamePhase == GamePhase.SUDDEN_DEATH) {
	show = true;
	pos = floor((g.game_length-g.game_timer)/(room_speed*(g.game_length/(room_speed*16))));
	if (g.currentCarPhase == CarPhase.COUNTDOWN_TO_START && (g.move_to_position_timer % room_speed < room_speed/2)) {
		show = false;
	}
} else if (g.currentGamePhase == GamePhase.COUNTDOWN_TO_START) {
	show = true;
	pos = 0;
	if (g.game_timer % room_speed < room_speed/2) {
		show = false;
	}
}

if (show) {
	draw_sprite_ext(spr_timer, pos, 1920/2, 1080/2,1,1,0,c_white,0.5);
}
draw_text(1770, 70, string(g.game_timer % room_speed > room_speed/2));
draw_text(1770, 140, string(g.currentCarPhase));