var newState=argument[0];

var prev_state = obj_gameplay.currentGamePhase;
obj_gameplay.currentGamePhase = newState;
show_debug_message("Switching currentGamePhase = " + string(newState));

switch (obj_gameplay.currentGamePhase) {
	case GamePhase.GAME_END_ANIMATION:
		obj_gameplay.game_timer=obj_gameplay.win_animation_length;
		with (obj_car_with_physics) {
			ready=0;
		}
		instance_destroy(obj_ball);
		scr_send_gameresult();
		with (obj_car_with_physics) {
			show_user_select_id_hist=show_user_select_id;
			show_user_select_id=0;
			show_user_select_name="";
			client_name="";
		}
		break;
}