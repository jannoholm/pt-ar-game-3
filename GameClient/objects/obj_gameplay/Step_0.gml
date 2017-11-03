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
		show_debug_message("Switching currentCarPhase = CarPhase.COUNTDOWN_TO_START");
		move_to_position_timer=move_to_position_length;
		return;
	case (CarPhase.COUNTDOWN_TO_START):
	
		if ( currentGamePhase == GamePhase.PLAY && move_to_position_timer mod room_speed == 0 ) {
			show_debug_message("Move to position timer mod room_speed == 0, playing countdown tone");
			audio_play_sound(snd_countdown_beep, 7, false);
		}
		
		move_to_position_timer=move_to_position_timer-1;
		if ( move_to_position_timer <= 0 ) {
			// TODO: Add some nice graphics
			if (currentGamePhase == GamePhase.PLAY) {
				instance_create_layer(room_width/2, room_height/2, "car", obj_ball);
				instance_create_layer(room_width/2, room_height/2, "car", obj_go);
				
				audio_play_sound(snd_countdown_go, 7, false);
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
			show_debug_message("Switching currentGamePhase = GamePhase.COUNTDOWN_TO_START");
			audio_play_sound(snd_countdown_beep, 7, false);
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
			
			audio_play_sound(snd_countdown_go, 7, false);
		} else if ( game_timer mod room_speed == 0 ) {
			show_debug_message("Game timer mod room_speed == 0, playing countdown tone");
			audio_play_sound(snd_countdown_beep, 7, false);
		}
		break;
	case (GamePhase.PLAY):
		if ( game_timer <= 0 ) {
			if (teamRedScore == teamBlueScore) {
				currentGamePhase = GamePhase.SUDDEN_DEATH;
				instance_create_layer(room_width/2, room_height/2, "car", obj_sudden_death);
				game_timer=sudden_death_length;
			} else {
				scr_change_gamestate(GamePhase.GAME_END_ANIMATION);
			}
		}
		break;
	case (GamePhase.SUDDEN_DEATH):
		if ( game_timer <= 0 ) {
			scr_change_gamestate(GamePhase.GAME_END_ANIMATION);
		}
		if ( suddendeath_sound == noone ) {
			suddendeath_sound = audio_play_sound(snd_suddendeath, 1, false);
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
		if ( gameend_sound == noone ) {
			gameend_sound = audio_play_sound(snd_gameend, 1, false);
		}
		break;
}

if ( ( currentGamePhase == GamePhase.PLAY || currentGamePhase == GamePhase.SUDDEN_DEATH ) &&  game_timer < room_speed * 10 && game_timer > 0 && game_timer mod room_speed == 0 ) {
		// Play warning sound if game phase end is abaout to approach each second
		audio_play_sound(snd_gameend_warning, 7, false);
}

game_timer=game_timer-1;


if ( suddendeath_sound != noone && currentGamePhase != GamePhase.SUDDEN_DEATH ) {
	show_debug_message("Reseting sudden death sound")
	suddendeath_sound = noone;
}
if ( gameend_sound != noone && currentGamePhase != GamePhase.GAME_END_ANIMATION ) {
	show_debug_message("Reseting game end sound")
	gameend_sound = noone;
}

