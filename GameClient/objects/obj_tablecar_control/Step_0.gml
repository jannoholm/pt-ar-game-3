/// @description Gamepad control

var currentGamePad = ds_map_find_value(global.gamepads, car.carId);
// Acceleration control - rest position value is -1 and fully pressed value is 1; the end value has to be between 0..1
var forwardAccelValue = (gamepad_axis_value(currentGamePad, global.gp_axis_forward) + 1) / 2;
var reverseAccelValue = (gamepad_axis_value(currentGamePad, global.gp_axis_reverse) + 1) / 2;
var goMoveGamepadValue = forwardAccelValue - reverseAccelValue;

// Ignore small deadzone
if ( abs(goMoveGamepadValue) > 0.05 ) {
	car.go_move = goMoveGamepadValue;
} else if (keyboard_check(car.keyboard_enabler)) {
	car.go_move = keyboard_check(vk_up)-keyboard_check(vk_down);
} else if (gamepad_button_check(currentGamePad, global.gp_pad_forward)) {
	car.go_move = 1;
} else if (gamepad_button_check(currentGamePad, global.gp_pad_reverse)) {
	car.go_move = -1;
} else {
	car.go_move = 0;
}


// Ignore small deadzone
if ( abs(gamepad_axis_value(currentGamePad, global.gp_axis_turn)) > 0.05 ) {
	car.go_turn = gamepad_axis_value(currentGamePad, global.gp_axis_turn);
} else if ( abs(gamepad_axis_value(currentGamePad, global.gp_axis_turn_alt)) > 0.05 ) {
	car.go_turn = gamepad_axis_value(currentGamePad, global.gp_axis_turn_alt);
} else if (keyboard_check(car.keyboard_enabler)) {
	car.go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
} else {
	car.go_turn = 0;
}

car.boost = keyboard_check(car.keyboard_enabler) && keyboard_check(vk_control) 
				|| gamepad_button_check(currentGamePad, global.gp_button_boost1) 
				|| gamepad_button_check(currentGamePad, global.gp_button_boost2) 
				|| gamepad_button_check(currentGamePad, global.gp_button_boost3);
car.shoot = keyboard_check(car.keyboard_enabler) && keyboard_check(vk_space) || gamepad_button_check(currentGamePad, global.gp_button_shoot);
car.highlight = keyboard_check(car.keyboard_enabler) && keyboard_check(ord("A")) || gamepad_button_check(currentGamePad, global.gp_button_highlight);

// choose player. drive buttons are used to scroll and cannot drive
var new_user_select=keyboard_check(car.keyboard_enabler) && keyboard_check(ord("X")) || gamepad_button_check(currentGamePad, global.gp_button_select_user);
if (!car.show_user_select && new_user_select) {
	car.show_user_select=new_user_select;
	scr_get_users_request();
} else if (car.show_user_select && !new_user_select) {
	car.show_user_select=new_user_select;
	scr_update_name_in_table(car);
}

// TODO: Send to Bluetooth controller via proxy