/// @description player movement
var hinput = keyboard_check(KEY_RIGHT) - keyboard_check(KEY_LEFT);

if (hinput != 0) {
	hspeed_player += hinput*acceleration_player;
	hspeed_player = clamp(hspeed_player, -hspeed_player_max, hspeed_player_max);
} else {
	hspeed_player = lerp(hspeed_player, 0, friction_player);
}

if (!place_meeting(x,y+1,obj_solid)) {
	vspeed_player += gravity_player;
} else {
	if (keyboard_check(KEY_UP)) {
		vspeed_player = jump_height;
	}
}

if ( hspeed_player != 0 ) {
	if (place_meeting(x+hspeed_player, y, obj_solid)) {
		while ( !place_meeting(x+sign(hspeed_player), y, obj_solid) ) {
			x += sign(hspeed_player)
		}
		hspeed_player = 0;
	} else {
		x += hspeed_player;
	}
}

if ( vspeed_player != 0 ) {
	if (place_meeting(x, y+vspeed_player, obj_solid)) {
		while ( !place_meeting(x, y+sign(vspeed_player), obj_solid) ) {
			y += sign(vspeed_player)
		}
		vspeed_player = 0;
	} else {
		y += vspeed_player;
	}
}