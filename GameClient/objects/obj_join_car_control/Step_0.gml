/// @description control car

// Acceleration control - rest position value is -1 and fully pressed value is 1; the end value has to be between 0..1
var forwardAccelValue = (gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_forward) + 1) / 2;
var reverseAccelValue = (gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_reverse) + 1) / 2;
var goMoveGamepadValue = forwardAccelValue - reverseAccelValue;

var goTurnGamepadValue = gamepad_axis_value(global.gamepadDeviceId, global.gp_axis_turn);

var go_move, go_turn, boost, shoot, highlight;

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && abs(goMoveGamepadValue) > 0.05 ) {
	go_move = goMoveGamepadValue;
} else {
	go_move = keyboard_check(vk_up)-keyboard_check(vk_down);;
}

// Ignore small deadzone
if ( global.gamepadDeviceId != -1 && abs(goTurnGamepadValue) > 0.05 ) {
	go_turn = goTurnGamepadValue;
} else {
	go_turn = keyboard_check(vk_right)-keyboard_check(vk_left);
}

boost = keyboard_check(vk_control) || global.gamepadDeviceId != -1 && (gamepad_button_check(global.gamepadDeviceId, global.gp_button_boost1) || gamepad_button_check(global.gamepadDeviceId, global.gp_button_boost2));
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