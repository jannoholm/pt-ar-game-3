/// @description Gamepad system conrol

show_debug_message("Event = " + async_load[? "event_type"]);        // Debug cocde so you can see which event has been
show_debug_message("Pad = " + string(async_load[? "pad_index"]));   // triggered and the pad associated with it.

switch(async_load[? "event_type"]) {           // Parse the async_load map to see which event has been triggered

case "gamepad discovered":                     // A game pad has been discovered
    var pad = async_load[? "pad_index"];       // Get the pad index value from the async_load map
    gamepad_set_axis_deadzone(pad, 0.05);      // Set the "deadzone" for the axis
    gamepad_set_button_threshold(pad, 0.1);    // Set the "threshold" for the triggers
	
	if ( ds_map_find_value(global.gamepads, "red1") == -1 ) {
		ds_map_replace( global.gamepads, "red1", pad );
		gamepad_set_color(pad, c_red);
		show_debug_message("Adding gamepad to red1, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "red2") == -1 ) {
		ds_map_replace( global.gamepads, "red2", pad );
		gamepad_set_color(pad, c_orange);
		show_debug_message("Adding gamepad to red2, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "blue1") == -1 ) {
		ds_map_replace( global.gamepads, "blue1", pad );
		gamepad_set_color(pad, c_blue);
		show_debug_message("Adding gamepad to blue1, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "blue2") == -1 ) {
		ds_map_replace( global.gamepads, "blue2", pad );
		gamepad_set_color(pad, c_blue);
		show_debug_message("Adding gamepad to blue2, pad index=" + string(pad));
	}

    break;
case "gamepad lost":                           // Gamepad has been removed or otherwise disabled
    var pad = async_load[? "pad_index"];       // Get the pad index
	
	if ( ds_map_find_value(global.gamepads, "red1") == pad ) {
		ds_map_replace( global.gamepads, "red1", -1 );
		show_debug_message("Removing gamepad from red1, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "red2") == pad ) {
		ds_map_replace( global.gamepads, "red2", -1 );
		show_debug_message("Removing gamepad from red2, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "blue1") == pad ) {
		ds_map_replace( global.gamepads, "blue1", -1 );
		show_debug_message("Removing gamepad from blue1, pad index=" + string(pad));
		
	} else	if ( ds_map_find_value(global.gamepads, "blue2") == pad ) {
		ds_map_replace( global.gamepads, "blue2", -1 );
		show_debug_message("Removing gamepad from blue2, pad index=" + string(pad));
	}

    break;
}
