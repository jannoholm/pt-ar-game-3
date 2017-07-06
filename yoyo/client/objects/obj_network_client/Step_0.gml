/// @description 
/*
if (keyboard_check(vk_up)) {
	buffer_seek(buffer, buffer_seek_start, 0);
	buffer_write(buffer, buffer_u16, 1);
	buffer_write(buffer, buffer_u8, 1); 
	network_send_packet(socket, buffer, buffer_tell(buffer));
}
if (keyboard_check(vk_down)) {
	buffer_write(buffer, buffer_u16, 1);
	buffer_write(buffer, buffer_u8, 2); 
}
if (keyboard_check(vk_right)) {
	buffer_write(buffer, buffer_u16, 1);
	buffer_write(buffer, buffer_u8, 3);
}
if (keyboard_check(vk_left)) {
	buffer_write(buffer, buffer_u16, 1);
	buffer_write(buffer, buffer_u8, 4); 
}

if (keyboard_check(vk_up) || keyboard_check(vk_down) || keyboard_check(vk_right) || keyboard_check(vk_left)) {
	network_send_packet(socket, buffer, buffer_tell(buffer));
}
*/