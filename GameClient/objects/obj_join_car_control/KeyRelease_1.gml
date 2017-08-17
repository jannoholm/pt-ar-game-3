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