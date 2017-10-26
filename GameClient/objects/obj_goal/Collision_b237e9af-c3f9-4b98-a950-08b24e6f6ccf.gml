show_goal=room_speed*4;

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

	currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
	if (currentGamePhase == GamePhase.SUDDEN_DEATH) {
		currentGamePhase = GamePhase.GAME_END_ANIMATION;
		show_goal=0;
	}
}
if (obj_gameplay.currentGamePhase == GamePhase.GAME_END_ANIMATION) {
	show_goal=0;
}

with (obj_car_with_physics) {
	ready=0;
}

audio_play_sound(snd_goal_applause, 5, false);