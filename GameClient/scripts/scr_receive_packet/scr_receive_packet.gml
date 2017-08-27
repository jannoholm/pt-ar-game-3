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
		obj_server_client.last_ping=current_time;
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
			if (obj_server_client.client_type == 0) {
				scr_start_host_game("table");
				room_goto(3);
			}
		}
		break;
	case 2001: // get games response
		if (room == lobby) {
			scr_games_to_lobby(buffer);
		}
		break;
	case 2007: // join game response
		var error_code = buffer_read(buffer, buffer_s32);
		var error_message = buffer_read(buffer, buffer_string);
		show_debug_message("got join game response: " + string(error_code) + ":" + error_message);
		if (error_code != 0) {
			show_message("Unable to join: " + string(error_code) + ":" + error_message);
			room_goto(2);
		} else {
			obj_playerinit_physics.car_control = instance_create_layer(0, 0, "car", obj_join_car_control);
			var team = buffer_read(buffer, buffer_s8);
			var position = buffer_read(buffer, buffer_s8);
			if (team == 0) {
				if (position == 1) {
					obj_playerinit_physics.car_control.car = obj_playerinit_physics.red1;
					obj_playerinit_physics.car_control.car.remote_control=false;
				} else if (position == 2) {
					obj_playerinit_physics.car_control.car = obj_playerinit_physics.red2;
					obj_playerinit_physics.car_control.car.remote_control=false;
				}
			} else if (team == 1) {
				if (position == 1) {
					obj_playerinit_physics.car_control.car = obj_playerinit_physics.blue1;
					obj_playerinit_physics.car_control.car.remote_control=false;
				} else if (position == 2) {
					obj_playerinit_physics.car_control.car = obj_playerinit_physics.blue2;
					obj_playerinit_physics.car_control.car.remote_control=false;
				}
			}
		}
		break;
	case 2009: // host game response
		var error_code = buffer_read(buffer, buffer_s32);
		var error_message = buffer_read(buffer, buffer_string);
		show_debug_message("got host game response: " + string(error_code) + ":" + error_message);
		if (error_code != 0) {
			show_message("Unable to start hosting: " + string(error_code) + ":" + error_message);
		} else {
			obj_server_client.gameid = buffer_read(buffer, buffer_string);
			obj_playerinit_physics.red1.remote_control=false;
			obj_playerinit_physics.red2.remote_control=false;
			obj_playerinit_physics.blue1.remote_control=false;
			obj_playerinit_physics.blue2.remote_control=false;

			if (obj_server_client.client_type == 0) {
				return;
			}
			
			var car_control = instance_create_layer(0, 0, "car", obj_hostcar_control);
			car_control.car = obj_playerinit_physics.red1;

			// setup drivers car
			var space_pos = string_pos(" ", obj_server_client.client_name);
			if (space_pos != 0) {
				car_control.car.client_name = string_copy(obj_server_client.client_name, 1, space_pos);
			} else {
				car_control.car.client_name = obj_server_client.client_name;
			}
		}
		break;
	case 2010: // lobby update of joined clients
		scr_game_lobby_update(buffer);
		break;
	case 3000: // game control message
		scr_game_control_message(buffer);
		break;
	case 3004: // game update message
		scr_gameupdate(buffer);
		break;
	case 3006: // location notification
		scr_locationupdate(buffer);
		break;
}