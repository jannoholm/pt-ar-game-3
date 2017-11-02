if (g.currentGamePhase == GamePhase.PLAY || g.currentGamePhase == GamePhase.SUDDEN_DEATH) {
	var pos = floor((g.game_length-g.game_timer)/(room_speed*(g.game_length/(room_speed*16))));
	draw_sprite_ext(spr_timer, pos, 1920/2, 1080/2,1,1,0,c_white,0.5);
}