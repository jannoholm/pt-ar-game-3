goal=goal+1;
show_goal=120;

if ( !obj_gameplay.joinedClient ) {
	// Only host should destroy the ball via network update
	instance_destroy(obj_ball);
}

with ( obj_gameplay ) {
	
	if ( other.team == "Red" ) {
		teamRedScore++;
	}
	
	if ( other.team == "Blue" ) {
		teamBlueScore++;
	}
	
	show_debug_message( "GOAL! Red:" + string(teamRedScore) + " Blue: " + string(teamBlueScore) );
	
	currentGamePhase = GamePhase.WAIT_TO_START;
}
