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
