var has_changes=false;
if (go_forward != keyboard_check(vk_up) || go_forward != keyboard_check(vk_up)) {
	has_changes=true;
}
if (go_right != keyboard_check(vk_right) || go_left != keyboard_check(vk_left)) {
	has_changes=true;
}
go_forward=keyboard_check(vk_up);
go_backward=keyboard_check(vk_down);
go_right=keyboard_check(vk_right);
go_left=keyboard_check(vk_left);

if (has_changes) {
	scr_send_game_control(gameid, client_id, go_forward, go_backward, go_right, go_left);
}