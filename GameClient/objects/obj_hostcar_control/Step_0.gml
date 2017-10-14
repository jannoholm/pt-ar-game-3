/// @description control host car


// Acceleration control - rest position value is -1 and fully pressed value is 1; the end value has to be between 0..1
var forwardAccelValue = (gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_forward) + 1) / 2;
var reverseAccelValue = (gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_reverse) + 1) / 2;
var goMoveGamepadValue = forwardAccelValue - reverseAccelValue;

var goTurnGamepadValue = gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_turn);


// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && abs(goMoveGamepadValue) > 0.05 ) {
	car.go_move = goMoveGamepadValue;
} else {
	car.go_move = keyboard_check(vk_up)-keyboard_check(vk_down);
}

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && abs(goTurnGamepadValue) > 0.05 ) {
	go_turn = goTurnGamepadValue;
} else {
	car.go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}

car.boost = keyboard_check(vk_control) || global.gamepadDeviceId != -1 && (gamepad_button_check(global.gamepadDeviceId, global.gp_button_boost1) || gamepad_button_check(global.gamepadDeviceId, global.gp_button_boost2));
car.shoot = keyboard_check(vk_space) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_shoot);
car.highlight = keyboard_check(ord("A")) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_highlight);

/*
// enable for debug. actually used with tablecar_control
// choose player. drive buttons are used to scroll and cannot drive
var new_user_select=keyboard_check(ord("X")) || global.gamepadDeviceId != -1 && gamepad_button_check(global.gamepadDeviceId, global.gp_button_select_user);
if (!car.show_user_select && new_user_select) {
	car.show_user_select=new_user_select;
	scr_get_users_request();
} else if (car.show_user_select && !new_user_select) {
	car.show_user_select=new_user_select;
	scr_update_name_in_table(car);
}
*/