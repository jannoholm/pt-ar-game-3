// out of game checks
keyboard_show=keyboard_check(keyboard_enabler);
if (obj_gameplay.currentGamePhase == GamePhase.WAIT_TO_START && shoot) {
	ready=1;
}

// do nothing, while car drives
if ( obj_gameplay.currentCarPhase == CarPhase.MOVE_TO_POSITIONS && !atPosition ) {
	atPosition = ai_reset_position();
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
if (show_user_select) {
	var show_user_select_pos=0;
	var set_values=false;
	if (show_user_select_id == 0) {
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
		if ( show_user_select_name == "AI_BOT_CHASER" ) {
			playerType = PlayerType.AI_CHASER;	
		} else if ( show_user_select_name == "AI_BOT_DEFENDER" ) {
			playerType = PlayerType.AI_DEFENDER;
		} else {
			playerType = PlayerType.PLAYER;	
		}
		
		if (show_user_select_pos > 0) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos-1);
			show_user_select_name_prev=show_user_selected.user_name;
		} else {
			show_user_select_name_prev="";
		}
		
		if (show_user_select_pos+1<ds_list_size(obj_server_client.user_name_list)) {
			var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos+1);
			show_user_select_name_next=show_user_selected.user_name;
		} else {
			show_user_select_name_next="";
		}
	}
	go_move_prev=go_move;
	return;
} else {
	show_user_select_scroll_speedup=0;
}

var leftWheelPower = 0;
var rightWheelPower = 0;

if ( damaged>0 ) {
	// drive uncontrollably, when shot
	if (damage_direction < 0) {
		rightWheelPower=2;
		leftWheelPower=0;
	} else {
		rightWheelPower=0;
		leftWheelPower=2;
	}
	if (damage_turn < 0) {
		rightWheelPower=-1*rightWheelPower;
		leftWheelPower=-1*leftWheelPower;
	}
} else if (colliding) {
	// drive uncontrollably, when on oil spill
	if (go_turn < 0) {
		rightWheelPower=2;
		leftWheelPower=0;
	} else {
		rightWheelPower=0;
		leftWheelPower=2;
	}
	if (go_move < 0) {
		rightWheelPower=-1*rightWheelPower;
		leftWheelPower=-1*leftWheelPower;
	}
	colliding=0;
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
	
	// apply boost
	if (boost && boost_power>0) {
	    boost_power=boost_power-room_speed;
	    rightWheelPower=rightWheelPower*2;
	    leftWheelPower=leftWheelPower*2;
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
physics_apply_impulse(x, y, -lvx*phy_mass, -lvy*phy_mass);


// Avoid sideways drifting by applying counter impulse
nx = lengthdir_x(1, (-phy_rotation)+90);
ny = lengthdir_y(1, (-phy_rotation)+90);

if (remote_control==false) {
	if (shoot && shoot_delay < 0) {
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
damaged=damaged-1;
shoot_delay=shoot_delay-1;
boost_power=clamp(boost_power+1, 0, boost_max);

