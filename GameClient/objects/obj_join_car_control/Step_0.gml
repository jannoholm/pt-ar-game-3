/// @description control car

/*
if ( global.gamepadDeviceId == -1 ) {
	// Gamepad not available
	return;
}*/

var go_move, go_turn, boost, shoot, highlight;

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && 
		abs(gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_speed)) > 0.05 ) {
	// Vertical axis is reversed on the gamepad
	go_move = -gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_speed)
} else {
	go_move = keyboard_check(vk_up)-keyboard_check(vk_down);;
}

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && 
		abs(gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_turn)) > 0.05 ) {
	go_turn = gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_turn)
} else {
	go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}


boost = keyboard_check(vk_control) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_boost);
shoot = keyboard_check(vk_space) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_shoot);
highlight = keyboard_check(ord("A")) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_highlight);

if (car.go_move != go_move || car.go_turn != go_turn || car.boost != boost || car.shoot != shoot || car.highlight != highlight) {
	car.go_move = go_move;
	car.go_turn = go_turn;
	car.boost = boost;
	car.shoot = shoot;
	car.highlight = highlight;
	scr_send_game_control(gameid, client_id, car.go_move, car.go_turn, car.boost, car.shoot, car.highlight);
}