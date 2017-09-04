///@description Control game flow
switch (currentGamePhase) {
	case (GamePhase.WAIT_TO_START):
	
		// TODO: Add logic to detect cards on fields, once all cars are on field move to next phase
		currentGamePhase = GamePhase.MOVE_TO_POSITIONS;
	
		break;
	case (GamePhase.MOVE_TO_POSITIONS):

		for ( var i = 0; i < instance_number(obj_car_with_physics); i += 1 ) {
			var car = instance_find( obj_car_with_physics, i );
			if ( !car.atPosition ) {
				// One of the cars is not ready yet, continue with reset movement
				return;
			}
		}
		
		show_debug_message("All cars at position, proceeding to countdown");
		
		// All cars at position, move to next phase
		currentGamePhase = GamePhase.COUNTDOWN_TO_START;
		
		break;
	case (GamePhase.COUNTDOWN_TO_START):
	
		// TODO: Add some nice graphics
		
		instance_create_layer(room_width/2, room_height/2, "car", obj_ball);
		currentGamePhase = GamePhase.PLAY;
	
		break;
	case (GamePhase.PLAY):
	
		break;
}

