// out of game checks
keyboard_show=keyboard_check(keyboard_enabler);
ready_delay=ready_delay-1;
if ((obj_gameplay.currentGamePhase == GamePhase.WAIT_TO_START && show_user_select_id !=0
		|| obj_gameplay.currentGamePhase == GamePhase.GAME_END_ANIMATION
		|| obj_gameplay.currentGamePhase == GamePhase.PLAY && obj_gameplay.currentCarPhase == CarPhase.WAIT_TO_START) 
		&& shoot && ready_delay < 0) {
	ready_delay=ready_interval;
	ready=!ready;
}

// do nothing, while car drives
if ( obj_gameplay.currentCarPhase == CarPhase.MOVE_TO_POSITIONS && !atPosition ) {
	atPosition = ai_reset_position();
	
	// Move faster when moving to position
	aiLeftWheelPower = aiLeftWheelPower*2;
	aiRightWheelPower = aiRightWheelPower*2;

} else if ( obj_gameplay.currentCarPhase != CarPhase.MOVE_TO_POSITIONS && atPosition ){
	// Reset at position step when game starts
	atPosition = false;
}


// allow nothing, if game not in proper state
else if ( (obj_gameplay.currentCarPhase != CarPhase.PLAY || obj_gameplay.currentGamePhase != GamePhase.PLAY) 
			&& (obj_gameplay.currentCarPhase != CarPhase.PLAY || obj_gameplay.currentGamePhase != GamePhase.SUDDEN_DEATH)
			&& !show_user_select ) {
	// If gameplay is not ongoing, don't allow movement
	return;
}

// user select
show_user_select_scroll_cooldown=show_user_select_scroll_cooldown-1;
if (show_user_select && !( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH )) {
	var show_user_select_pos=0;
	var set_values=false;
	if (show_user_select_id == 0 && show_user_select_id_hist != 0) {
		show_user_select_id=show_user_select_id_hist;
		// find position of current element
		var show_user_select_pos=0;
		var i;
		for (i=0; i < ds_list_size(obj_server_client.user_name_list); ++i) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, i);
			if (show_user_selected.user_id == show_user_select_id) {
				show_user_select_pos=i;
				break;
			}
		}
		set_values=true;
	} else if (show_user_select_id == 0) {
		set_values=true;
	} else if (show_user_select_scroll_cooldown > 0 ) {
		// don't move
	} else if (go_move != 0 || go_move != 0 && sign(go_move) != sign(go_move_prev)) {
		if ( show_user_select_scroll_cooldown=0 ) {
			show_user_select_scroll_speedup=show_user_select_scroll_speedup+1;
		}
		show_user_select_scroll_cooldown=clamp(floor(room_speed/6)-floor(show_user_select_scroll_speedup/2),2,100);
		set_values=true;
		
		// find position of current element
		var show_user_select_pos=0;
		var i;
		for (i=0; i < ds_list_size(obj_server_client.user_name_list); ++i) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, i);
			if (show_user_selected.user_id == show_user_select_id) {
				show_user_select_pos=i;
				break;
			}
		}
		
		// scroll user
		if (go_move > 0) {
			if (show_user_select_pos > 0) {
				show_user_select_pos=show_user_select_pos-1;
			}
		} else if (go_move < 0) {
			if (show_user_select_pos+1 < ds_list_size(obj_server_client.user_name_list)) {
				show_user_select_pos=show_user_select_pos+1;
			}
		}
	} else {
		show_user_select_scroll_speedup=0;
	}
	
	// set data for scrolled user
	if (set_values && ds_list_size(obj_server_client.user_name_list) > 0) {
		var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos);
		show_user_select_id=show_user_selected.user_id;
		show_user_select_name=show_user_selected.user_name;
		
		// Hardcoded user names to AI
		if ( show_user_select_name == "ai_bot_chaser" ) {
			playerType = PlayerType.AI_CHASER;	
		} else if ( show_user_select_name == "ai_bot_defender" ) {
			playerType = PlayerType.AI_DEFENDER;
		} else {
			playerType = PlayerType.PLAYER;	
		}
		
		show_user_select_name_prev1="";
		show_user_select_name_prev2="";
		if (show_user_select_pos > 0) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos-1);
			show_user_select_name_prev1=show_user_selected.user_name;
			if (show_user_select_pos > 1) {
				var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos-2);
				show_user_select_name_prev2=show_user_selected.user_name;
			}
		}
		
		show_user_select_name_next1="";
		show_user_select_name_next2="";
		if (show_user_select_pos+1<ds_list_size(obj_server_client.user_name_list)) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos+1);
			show_user_select_name_next1=show_user_selected.user_name;
			if (show_user_select_pos+2<ds_list_size(obj_server_client.user_name_list)) {
				var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos+2);
				show_user_select_name_next2=show_user_selected.user_name;
			}
		}
	}
	go_move_prev=go_move;
	return;
} else {
	show_user_select_scroll_speedup=0;
}


if ( !colliding && oilspill_tire_sound != noone ) {
	// If car is not colliding with the oil spill anymore, but the sound is still playing, then stop the sound
	audio_stop_sound(oilspill_tire_sound);
	oilspill_tire_sound = noone;
}

var leftWheelPower = 0;
var rightWheelPower = 0;

if ( damaged>0 && ( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH ) && obj_gameplay.currentCarPhase == CarPhase.PLAY ) {
	// drive uncontrollably, when shot
	if (damage_direction < 0) {
		rightWheelPower=-2;
		leftWheelPower=0;
	} else {
		rightWheelPower=2;
		leftWheelPower=0;
	}
	if (damage_turn < 0) {
		leftWheelPower=rightWheelPower;
		rightWheelPower=0;
	}
} else if (colliding && ( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH ) && obj_gameplay.currentCarPhase == CarPhase.PLAY) {
	// drive uncontrollably, when on oil spill
	if (go_turn >= 0) {
		rightWheelPower=1;
		leftWheelPower=0;
	} else {
		rightWheelPower=0;
		leftWheelPower=1;
	}
	if (go_move < 0) {
		rightWheelPower=-1*rightWheelPower;
		leftWheelPower=-1*leftWheelPower;
	}
	
	if ( oilspill_tire_sound == noone ) {
		// Car just entered the oil spill, start tire screech sound
		oilspill_tire_sound = audio_play_sound(snd_oil_tire_squal, 1, false);
		audio_sound_set_track_position(oilspill_tire_sound, random_range(3, 12));	
	}

} else if ( playerType == PlayerType.AI_CHASER && go_move == 0 && go_turn == 0 && obj_gameplay.currentCarPhase == CarPhase.PLAY && instance_exists(obj_ball) ) {
	// Allow AI to control only if player is not overriding, using aiLeftWheelPower and aiRightWheelPower params via scripts
	ai_car_chaser();
} else if ( playerType == PlayerType.AI_DEFENDER && go_move == 0 && go_turn == 0 && obj_gameplay.currentCarPhase == CarPhase.PLAY && instance_exists(obj_ball) ) {
	// Allow AI to control only if player is not overriding, using aiLeftWheelPower and aiRightWheelPower params via scripts
	ai_car_defender();
} else {
	if (go_move > 0) {
		if (go_turn >= 0) {
			rightWheelPower=1+0.7*go_turn;
			leftWheelPower=2-rightWheelPower;
		} else if (go_turn < 0) {
			leftWheelPower=1+0.7*go_turn*(-1);
			rightWheelPower=2-leftWheelPower;
		}
		rightWheelPower=rightWheelPower*go_move;
		leftWheelPower=leftWheelPower*go_move;
	} else if (go_move < 0) {
		if (go_turn >= 0) {
			rightWheelPower=1+0.7*go_turn;
			leftWheelPower=2-rightWheelPower;
		} else if (go_turn < 0) {
			leftWheelPower=1+0.7*go_turn*(-1);
			rightWheelPower=2-leftWheelPower;
		}
		rightWheelPower=rightWheelPower*go_move;
		leftWheelPower=leftWheelPower*go_move;
	}
	
	// apply boost if player is driving
	if (boost && go_move != 0 && boost_power>0 && ( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH ) && obj_gameplay.currentCarPhase == CarPhase.PLAY) {
	    boost_power=boost_power-boost_spend;
	    rightWheelPower=rightWheelPower*2;
	    leftWheelPower=leftWheelPower*2;
		
		// Create boost flame animation, but avoid flickering if low on power
		if ( !boost_flame_animated && boost_power > room_speed ) {
			boost_flame_animated = true;
			boostFlame = instance_create_layer(x, y+boostFlameOffsetX, "car", obj_boost_fire);
		}
	} else {

		// Disable boost flame animation
		if ( boost_flame_animated ) {
			boost_flame_animated = false;
			instance_destroy(boostFlame)
		}
	}
}

// Apply physical force to tire locations
if ( leftWheelPower == 0 && rightWheelPower == 0 ) {
	// If no player or environment control is going on, then allow AI to move via scripts
	physics_apply_local_force(rearTireOffsetX, rearLeftTireOffsetY, aiLeftWheelPower*15000, 0);
	physics_apply_local_force(rearTireOffsetX, rearRightTireOffsetY, aiRightWheelPower*15000, 0);
	aiLeftWheelPower = 0;
	aiRightWheelPower = 0;
} else {
	physics_apply_local_force(rearTireOffsetX, rearLeftTireOffsetY, leftWheelPower*15000, 0);
	physics_apply_local_force(rearTireOffsetX, rearRightTireOffsetY, rightWheelPower*15000, 0);
}

// Avoid sideways drifting by applying counter impulse
nx = lengthdir_x(1, (-phy_rotation)+90);
ny = lengthdir_y(1, (-phy_rotation)+90);
dot = dot_product(nx, ny, phy_linear_velocity_x * world_size, phy_linear_velocity_y * world_size);
lvx = dot*nx;
lvy = dot*ny;
//physics_apply_impulse(x, y, -lvx*phy_mass, -lvy*phy_mass);


// Avoid sideways drifting by applying counter impulse
nx = lengthdir_x(1, (-phy_rotation)+90);
ny = lengthdir_y(1, (-phy_rotation)+90);

if (remote_control==false) {
	if (shoot && shoot_delay < 0 && ( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH ) && obj_gameplay.currentCarPhase == CarPhase.PLAY) {
		shoot_delay=shoot_interval;

		var pos_x = x+dcos((-1)*phy_rotation)*sprite_width;
		var pos_y = y+dsin(phy_rotation)*sprite_width;
		var bullet=instance_create_layer(pos_x, pos_y, "car", obj_bullet);
		bullet.phy_rotation = phy_rotation;
		bullet.shooter=self;
		with(bullet){
			physics_apply_local_force(0, 0, phy_mass*2500, 0);
		}
		ds_list_add(bullets, bullet);
	}
}

if (( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH ) && obj_gameplay.currentCarPhase == CarPhase.PLAY) {
	damaged=damaged-1;
	shoot_delay=shoot_delay-1;
	boost_power=clamp(boost_power+1, -room_speed, boost_max);
}

colliding = false;
