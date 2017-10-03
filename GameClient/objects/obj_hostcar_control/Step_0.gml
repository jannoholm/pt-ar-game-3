/// @description control host car


// Ignore small deadzone
if ( global.gamepadDeviceId != -1 &&
		abs(gamepad_axis_value(global.gamepadDeviceId, gp_axislv)) > 0.05 ) {
	// Vertical axis is reversed on the gamepad
	car.go_move = -gamepad_axis_value(global.gamepadDeviceId, gp_axislv);
} else {
	car.go_move = keyboard_check(vk_up)-keyboard_check(vk_down);
}

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 &&
		abs(gamepad_axis_value(global.gamepadDeviceId, gp_axisrh)) > 0.05 ) {
	car.go_turn = gamepad_axis_value(global.gamepadDeviceId, gp_axisrh);
} else {
	car.go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}

car.boost = keyboard_check(vk_control) || global.gamepadDeviceId != -1 && gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickl)
car.shoot = keyboard_check(vk_space) || global.gamepadDeviceId != -1 && gamepad_button_check_pressed(global.gamepadDeviceId, gp_stickr)
car.highlight=keyboard_check(ord("A")) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, gp_face4);

/*
// enable for debug. actually used with tablecar_control
// choose player. drive buttons are used to scroll and cannot drive
var new_user_select=keyboard_check(ord("X")) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, gp_face1);
if (!car.show_user_select && new_user_select) {
	car.show_user_select=new_user_select;
	scr_get_users_request();
} else if (car.show_user_select && !new_user_select) {
	car.show_user_select=new_user_select;
	scr_update_name_in_table(car);
}
*/