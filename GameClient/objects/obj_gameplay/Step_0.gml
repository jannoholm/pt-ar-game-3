///@description Control game flow
switch (currentCarPhase) {
	case (CarPhase.MOVE_TO_POSITIONS):

		for ( var i = 0; i < instance_number(obj_car_with_physics); i += 1 ) {
			var car = instance_find( obj_car_with_physics, i );
			if ( !car.atPosition ) {
				// One of the cars is not ready yet, continue with reset movement
				return;
			}
		}
		
		show_debug_message("All cars at position, proceeding to countdown");
		
		// All cars at position, move to next phase
		currentCarPhase = CarPhase.COUNTDOWN_TO_START;
		
		break;
	case (CarPhase.COUNTDOWN_TO_START):
	
		// TODO: Add some nice graphics
		
		instance_create_layer(room_width/2, room_height/2, "car", obj_ball);
		currentCarPhase = CarPhase.PLAY;
	
		break;
	case (CarPhase.PLAY):
		break;
}

switch (currentGamePhase) {
	case (GamePhase.PREPARE_TO_START):
		currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
		currentGamePhase = GamePhase.WAIT_TO_START;
		scr_reset_game_for_start();
	case (GamePhase.WAIT_TO_START):
		// TODO
		currentGamePhase = GamePhase.COUNTDOWN_TO_START;
		game_timer = countdown_length;
		break;
	case (GamePhase.COUNTDOWN_TO_START):
		if ( game_timer <= 0 ) {
			currentGamePhase = GamePhase.PLAY;
			game_timer=game_length;
		}
		break;
	case (GamePhase.PLAY):
		if ( game_timer <= 0 ) {
			currentGamePhase = GamePhase.GAME_END_ANIMATION;
			game_timer=win_animation_length;
		}
		break;
	case (GamePhase.GAME_END_ANIMATION):
		currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
		if ( game_timer <= 0 ) {
			currentGamePhase = GamePhase.WAIT_TO_START;
			game_timer=game_length;
		}
		break;
}
game_timer=game_timer-1;
