enum GamePhase {
	WAIT_TO_START,
	MOVE_TO_POSITIONS,
	COUNTDOWN_TO_START,
	PLAY	
}

joinedClient = false;

currentGamePhase = GamePhase.WAIT_TO_START;

teamRedScore = 0;
teamBlueScore = 0;
