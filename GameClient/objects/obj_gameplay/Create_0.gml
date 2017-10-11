enum GamePhase {
	WAIT_TO_START,
	MOVE_TO_POSITIONS,
	COUNTDOWN_TO_START,
	PLAY
}

enum GamePhaseRename {
	PLAY,
	GAME_END_ANIMATION
}

joinedClient = false;

currentGamePhase = GamePhase.WAIT_TO_START;
newPhase=GamePhaseRename.PLAY;

teamRedScore = 0;
teamBlueScore = 0;
game_length=room_speed*60*2; // 2min
//game_length=room_speed*10; // 10s
win_animation_length=room_speed*10;
game_timer=game_length;

