enum CarPhase {
	MOVE_TO_POSITIONS,
	WAIT_TO_START,
	COUNTDOWN_TO_START,
	PLAY
}

enum GamePhase {
	PREPARE_TO_START,
	WAIT_TO_START,
	COUNTDOWN_TO_START,
	PLAY,
	SUDDEN_DEATH,
	GAME_END_ANIMATION
}

joinedClient = false;

// state
currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
currentGamePhase=GamePhase.PREPARE_TO_START;

// score
teamRedScore = 0;
teamBlueScore = 0;

// timer conf
//game_length=room_speed*60*2; // 2min
game_length=room_speed*30; // 30s
sudden_death_length=room_speed*30; // 20s
win_animation_length=room_speed*30;
countdown_length=room_speed*3;
move_to_position_length=room_speed*2;

game_timer=game_length;
move_to_position_timer=0;

// Sound related
suddendeath_sound = noone;
gameend_sound = noone;
