/// scr_receive_packet
var buffer = argument[0];
var cmd_type = buffer_read(buffer, buffer_u16);
switch (cmd_type) {
	case 1: // move command
		var key_dir = buffer_read(buffer, buffer_u8);
		scr_network_control_car(key_dir);
		break;
}