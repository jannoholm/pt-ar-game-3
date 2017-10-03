/// @description Gamepad control

var currentGamePad = ds_map_find_value(global.gamepads, car.carId);

if ( currentGamePad == -1 ) {
	// Gamepad not available
	return;
}

// Ignore small deadzone
if ( abs(gamepad_axis_value(currentGamePad, gp_axislv)) > 0.05 ) {
	// Vertical axis is reversed on the gamepad
	car.go_move = -gamepad_axis_value(currentGamePad, gp_axislv);
} else {
	car.go_move = keyboard_check(vk_up)-keyboard_check(vk_down);
}

// Ignore small deadzone
if ( abs(gamepad_axis_value(currentGamePad, gp_axisrh)) > 0.05 ) {
	car.go_turn = gamepad_axis_value(currentGamePad, gp_axisrh);
} else {
	car.go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}

car.boost = keyboard_check(vk_control) || gamepad_button_check_pressed(currentGamePad, gp_stickl)
car.shoot = keyboard_check(vk_space) || gamepad_button_check_pressed(currentGamePad, gp_stickr)
car.highlight=keyboard_check(ord("A")) || gamepad_button_check(currentGamePad, gp_face4);

// choose player. drive buttons are used to scroll and cannot drive
var new_user_select=keyboard_check(ord("X")) || gamepad_button_check(currentGamePad, gp_face1);
if (!car.show_user_select && new_user_select) {
	car.show_user_select=new_user_select;
	scr_get_users_request();
} else if (car.show_user_select && !new_user_select) {
	car.show_user_select=new_user_select;
	scr_update_name_in_table(car);
}

// TODO: Send to Bluetooth controller via proxy