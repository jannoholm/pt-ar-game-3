// Since GameMaker does not support PS4 gamepad under Ubuntu correctly, manual mapping is needed
if ( os_type == os_linux ) {
	// TODO: Update linux controls
} else {
	global.gp_axis_forward = 4; //gp_shoulderrb;
	global.gp_axis_reverse = 3; //gp_shoulderlb;
	global.gp_axis_turn = gp_axislh;
	global.gp_button_boost1 = gp_shoulderr;
	global.gp_button_boost2 = gp_face2;
	global.gp_button_shoot = gp_face3;
	global.gp_button_highlight = gp_face4;
	global.gp_button_select_user = gp_face1;
}

// By default, gamepads are disabled

// Host car control in case of local gameplay
global.gamepadDeviceId = -1

// Table mode gamepad control
global.gamepads = ds_map_create();

ds_map_replace( global.gamepads, "red1", -1 );
ds_map_replace( global.gamepads, "red2", -1 );
ds_map_replace( global.gamepads, "blue1", -1 );
ds_map_replace( global.gamepads, "blue2", -1 );


var gamepadCount = gamepad_get_device_count();

// Host/join car control a single gamepad - first gamepad found is bound
for ( var pad = 0; pad < gamepadCount; pad++ ) {
	if ( gamepad_is_connected(pad) ) {
		show_debug_message("Setting host/join client gamepad control to " + string(pad));
		global.gamepadDeviceId = pad;
		break;
	}
}

// Table mode gamepad control - fill for all cars
for ( var pad = 0; pad < gamepadCount; pad++ ) {
	if ( gamepad_is_connected(pad) ) {
		
		if ( ds_map_find_value(global.gamepads, "red1") == -1 ) {
			ds_map_replace( global.gamepads, "red1", pad );
			gamepad_set_color(pad, c_red);
			show_debug_message("Adding gamepad to red1, pad index=" + string(pad));
		
		} else if ( ds_map_find_value(global.gamepads, "red2") == -1 ) {
			ds_map_replace( global.gamepads, "red2", pad );
			gamepad_set_color(pad, c_orange);
			show_debug_message("Adding gamepad to red2, pad index=" + string(pad));
		
		} else if ( ds_map_find_value(global.gamepads, "blue1") == -1 ) {
			ds_map_replace( global.gamepads, "blue1", pad );
			gamepad_set_color(pad, c_blue);
			show_debug_message("Adding gamepad to blue1, pad index=" + string(pad));
		
		} else if ( ds_map_find_value(global.gamepads, "blue2") == -1 ) {
			ds_map_replace( global.gamepads, "blue2", pad );
			gamepad_set_color(pad, c_blue);
			show_debug_message("Adding gamepad to blue2, pad index=" + string(pad));
		}
	}
}
