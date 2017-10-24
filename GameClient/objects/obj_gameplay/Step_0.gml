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
		currentCarPhase = CarPhase.WAIT_TO_START;
		
		break;
	case (CarPhase.WAIT_TO_START):
		/*if (obj_playerinit_physics.red1.ready && obj_playerinit_physics.red2.ready
			&& obj_playerinit_physics.blue1.ready && obj_playerinit_physics.blue2.ready) {
				
			currentCarPhase = CarPhase.COUNTDOWN_TO_START;
			move_to_position_timer=move_to_position_length;
			show_debug_message("cars are ready");
		}*/
		currentCarPhase = CarPhase.COUNTDOWN_TO_START;
		move_to_position_timer=move_to_position_length;
		return;
	case (CarPhase.COUNTDOWN_TO_START):
		
		move_to_position_timer=move_to_position_timer-1;
		if ( move_to_position_timer <= 0 ) {
			// TODO: Add some nice graphics
			if (currentGamePhase == GamePhase.PLAY) {
				instance_create_layer(room_width/2, room_height/2, "car", obj_ball);
				instance_create_layer(room_width/2, room_height/2, "car", obj_go);
			}
			currentCarPhase = CarPhase.PLAY;
		} else {
			return;
		}
	
		break;
	case (CarPhase.PLAY):
		break;
}

switch (currentGamePhase) {
	case (GamePhase.PREPARE_TO_START):
		currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
		currentGamePhase = GamePhase.WAIT_TO_START;
		scr_reset_game_for_start();
		break;
	case (GamePhase.WAIT_TO_START):
		if (obj_playerinit_physics.red1.ready && obj_playerinit_physics.red2.ready
			&& obj_playerinit_physics.blue1.ready && obj_playerinit_physics.blue2.ready) {
				
			currentGamePhase = GamePhase.COUNTDOWN_TO_START;
			game_timer = countdown_length;
		}
		break;
	case (GamePhase.COUNTDOWN_TO_START):
		if ( game_timer <= 0 ) {
			currentGamePhase = GamePhase.PLAY;
			game_timer=game_length;
			instance_destroy(obj_ball);
			instance_create_layer(room_width/2, room_height/2, "car", obj_ball);
			instance_create_layer(room_width/2, room_height/2, "car", obj_go);
		}
		break;
	case (GamePhase.PLAY):
		if ( game_timer <= 0 ) {
			if (teamRedScore == teamBlueScore) {
				currentGamePhase = GamePhase.SUDDEN_DEATH;
				instance_create_layer(room_width/2, room_height/2, "car", obj_sudden_death);
				game_timer=sudden_death_length;
			} else {
				currentGamePhase = GamePhase.GAME_END_ANIMATION;
				game_timer=win_animation_length;
				with (obj_car_with_physics) {
					ready=0;
				}
				instance_destroy(obj_ball);
			}
		}
		break;
	case (GamePhase.SUDDEN_DEATH):
		if ( game_timer <= 0 ) {
			currentGamePhase = GamePhase.GAME_END_ANIMATION;
			game_timer=win_animation_length;
			with (obj_car_with_physics) {
				ready=0;
			}
			instance_destroy(obj_ball);
		}
		break;
	case (GamePhase.GAME_END_ANIMATION):
		currentCarPhase = CarPhase.MOVE_TO_POSITIONS;
		if ( game_timer <= 0 || 
				obj_playerinit_physics.red1.ready && obj_playerinit_physics.red2.ready
				&& obj_playerinit_physics.blue1.ready && obj_playerinit_physics.blue2.ready ) {
			currentGamePhase = GamePhase.PREPARE_TO_START;
			game_timer=game_length;
		}
		break;
}
game_timer=game_timer-1;
