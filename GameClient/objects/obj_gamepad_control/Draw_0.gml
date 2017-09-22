



var gamepadCount = gamepad_get_device_count();

for ( var pad = 0; pad < gamepadCount; pad++ ) {

	if ( gamepad_button_check(pad, gp_face4) ) {
		if ( ds_map_find_value(global.gamepads, "red1") == pad ) {
			draw_set_color(c_red);
			draw_circle(obj_playerinit_physics.red1.x, obj_playerinit_physics.red1.y, 50, false);
		}
		if ( ds_map_find_value(global.gamepads, "red2") == pad ) {
			draw_set_color(c_orange);
			draw_circle(obj_playerinit_physics.red2.x, obj_playerinit_physics.red2.y, 50, false);
		}
		if ( ds_map_find_value(global.gamepads, "blue1") == pad ) {
			draw_set_color(c_blue);
			draw_circle(obj_playerinit_physics.blue1.x, obj_playerinit_physics.blue1.y, 50, false);
		}
		if ( ds_map_find_value(global.gamepads, "blue2") == pad ) {
			draw_set_color(c_aqua);
			draw_circle(obj_playerinit_physics.blue2.x, obj_playerinit_physics.blue2.y, 50, false);
		}
	}
}