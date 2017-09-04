/// @description Gamepad control

if ( global.gamepadDeviceId == -1 ) {
	// Gamepad not available
	return;
}

// Ignore small deadzone
if ( abs(gamepad_axis_value(global.gamepadDeviceId, gp_axislv)) > 0.05 ) {
	// Vertical axis is reversed on the gamepad
	car.go_move = -gamepad_axis_value(global.gamepadDeviceId, gp_axislv);
} else {
	car.go_move = keyboard_check(vk_up)-keyboard_check(vk_down);
}

// Ignore small deadzone
if ( abs(gamepad_axis_value(global.gamepadDeviceId, gp_axisrh)) > 0.05 ) {
	car.go_turn = gamepad_axis_value(global.gamepadDeviceId, gp_axisrh);
} else {
	car.go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}

car.boost = keyboard_check(vk_control) || gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickl)
car.shoot = keyboard_check(vk_space) || gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickr)
