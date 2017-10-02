/// @description Gamepad control

if ( global.gamepadDeviceId == -1 ) {
	// Gamepad not available
	return;
}

var go_move, go_turn, boost, shoot, highlight;

// Ignore small deadzone
if ( abs(gamepad_axis_value(global.gamepadDeviceId, gp_axislv)) > 0.05 ) {
	// Vertical axis is reversed on the gamepad
	go_move = -gamepad_axis_value(global.gamepadDeviceId, gp_axislv)
} else {
	go_move = keyboard_check(vk_up)-keyboard_check(vk_down);;
}

// Ignore small deadzone
if ( abs(gamepad_axis_value(global.gamepadDeviceId, gp_axisrh)) > 0.05 ) {
	go_turn = gamepad_axis_value(global.gamepadDeviceId, gp_axisrh)
} else {
	go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}


boost = keyboard_check(vk_control) || gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickl);
shoot = keyboard_check(vk_space) || gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickr);
highlight = keyboard_check(ord("A")) || gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickr);

if (car.go_move != go_move || car.go_turn != go_turn || car.boost != boost || car.shoot != shoot || car.highlight != highlight) {
	car.go_move = go_move;
	car.go_turn = go_turn;
	car.boost = boost;
	car.shoot = shoot;
	car.highlight = highlight;
	scr_send_game_control(gameid, client_id, car.go_move, car.go_turn, car.boost, car.shoot, car.highlight);
}