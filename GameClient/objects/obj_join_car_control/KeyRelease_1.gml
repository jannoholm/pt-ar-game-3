var go_move=keyboard_check(vk_up)-keyboard_check(vk_down);
var go_turn=keyboard_check(vk_right)-keyboard_check(vk_left);

if (car.go_move != go_move || car.go_turn != go_turn) {
	car.go_move = go_move;
	car.go_turn = go_turn;
	scr_send_game_control(gameid, client_id, car.go_move, car.go_turn);
}
/*
var has_changes=false;
if (car.go_forward != keyboard_check(vk_up) || car.go_backward != keyboard_check(vk_down)) {
	has_changes=true;
}
if (car.go_right != keyboard_check(vk_right) || car.go_left != keyboard_check(vk_left)) {
	has_changes=true;
}
car.go_forward=keyboard_check(vk_up);
car.go_backward=keyboard_check(vk_down);
car.go_right=keyboard_check(vk_right);
car.go_left=keyboard_check(vk_left);

if (has_changes) {
	scr_send_game_control(gameid, client_id, car.go_forward, car.go_backward, car.go_right, car.go_left);
}
*/