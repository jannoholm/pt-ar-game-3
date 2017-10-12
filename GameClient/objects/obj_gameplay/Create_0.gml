enum CarPhase {
	MOVE_TO_POSITIONS,
	COUNTDOWN_TO_START,
	PLAY
}

enum GamePhase {
	PREPARE_TO_START,
	WAIT_TO_START,
	COUNTDOWN_TO_START,
	PLAY,
	GAME_END_ANIMATION
}

joinedClient = false;

currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
currentGamePhase=GamePhase.PREPARE_TO_START;

teamRedScore = 0;
teamBlueScore = 0;
game_length=room_speed*60*2; // 2min
//game_length=room_speed*10; // 10s
win_animation_length=room_speed*10;
countdown_length=room_speed*5;
game_timer=game_length;

