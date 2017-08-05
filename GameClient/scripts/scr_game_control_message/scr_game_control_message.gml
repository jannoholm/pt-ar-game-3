var key_dir = argument[0];
show_debug_message("keydir=" + string(key_dir))
switch (key_dir) {
	case 1:
		keyboard_key_press(vk_up);
		break;
	case 2:
		keyboard_key_press(vk_down);
		break;
	case 3:
		keyboard_key_press(vk_right);
		break;
	case 4:
		keyboard_key_press(vk_left);
		break;
}