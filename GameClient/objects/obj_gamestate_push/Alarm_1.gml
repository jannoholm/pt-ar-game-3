if (
	(obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH)
	&& obj_gameplay.currentCarPhase == CarPhase.PLAY) {
	
	scr_gameupdate_broadcast();
}
alarm[1] = game_update_interval;