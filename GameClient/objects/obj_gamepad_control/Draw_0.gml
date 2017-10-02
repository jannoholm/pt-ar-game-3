var gamepadCount = gamepad_get_device_count();

for ( var pad = 0; pad < gamepadCount; pad++ ) {

	var circleX, circleY, circleColor;

	if ( gamepad_is_connected(pad) && gamepad_button_check(pad, gp_face4) ) {
		if ( ds_map_find_value(global.gamepads, "red1") == pad ) {
			circleX = obj_playerinit_physics.red1.x;
			circleY = obj_playerinit_physics.red1.y;
		}
		if ( ds_map_find_value(global.gamepads, "red2") == pad ) {
			circleX = obj_playerinit_physics.red2.x;
			circleY = obj_playerinit_physics.red2.y;
		}
		if ( ds_map_find_value(global.gamepads, "blue1") == pad ) {
			circleX = obj_playerinit_physics.blue1.x;
			circleY = obj_playerinit_physics.blue1.y;
		}
		if ( ds_map_find_value(global.gamepads, "blue2") == pad ) {
			circleX = obj_playerinit_physics.blue2.x;
			circleY = obj_playerinit_physics.blue2.y;
		}
		
		draw_sprite_ext(spr_car_highlight, 0, circleX, circleY, 1, 1, image_angle, c_white, 1);
	}
}