/// scr_receive_packet
var buffer = argument[0];

// parse header
// read byte count of header
var packet_size = buffer_read(buffer, buffer_s32);
var header_size = buffer_read(buffer, buffer_s32);
var message_type = buffer_read(buffer, buffer_s32);
var message_id = buffer_read(buffer, buffer_u64);
var client_id = buffer_read(buffer, buffer_string);

switch (message_type) {
	case 1000: // ping request
		// send ping response
		scr_write_messageheader(obj_server_client.out_buffer, 1001, message_id, client_id);
		scr_send_packet(obj_server_client.out_buffer);
		show_debug_message("ping processed");
		break;
	case 1003: // join server response
		var error_code = buffer_read(buffer, buffer_s32);
		var error_message = buffer_read(buffer, buffer_string);
		show_debug_message("got join response: " + client_id + ", " + string(error_code) + ":" + error_message);
		if (error_code != 0) {
			show_message("Unable to join server: " + string(error_code) + ":" + error_message);
		} else {
			room_goto_next();
			obj_server_client.client_id = client_id;
			show_debug_message("obj_server_client.client_id=" + obj_server_client.client_id);
		}
		break;
	case 2001: // get games response
		show_debug_message("got games response");
		scr_games_to_lobby(buffer);
		break;
	case 3000: // game control message
		var key_dir = buffer_read(buffer, buffer_u8);
		scr_game_control_message(key_dir);
		break;
	case 3002: // game update message
		break;
}