goal=goal+1;
show_goal=120;

if ( !obj_gameplay.joinedClient ) {
	// Only host should destroy the ball via network update
	instance_destroy(obj_ball);
}

with ( obj_gameplay ) {
	
	if ( other.team == "Red" ) {
		teamRedScore++;
		if (obj_playerinit_physics.red1.score_last_touch < obj_playerinit_physics.red2.score_last_touch) {
			obj_playerinit_physics.red2.score_goals=obj_playerinit_physics.red2.score_goals+1;
		} else {
			obj_playerinit_physics.red1.score_goals=obj_playerinit_physics.red1.score_goals+1;
		}
	}
	
	if ( other.team == "Blue" ) {
		teamBlueScore++;
		if (obj_playerinit_physics.blue1.score_last_touch < obj_playerinit_physics.blue2.score_last_touch) {
			obj_playerinit_physics.blue2.score_goals=obj_playerinit_physics.blue2.score_goals+1;
		} else {
			obj_playerinit_physics.blue1.score_goals=obj_playerinit_physics.blue1.score_goals+1;
		}
	}
	
	show_debug_message( "GOAL! Red:" + string(teamRedScore) + " Blue: " + string(teamBlueScore) );
	
	currentGamePhase = GamePhase.WAIT_TO_START;
}
