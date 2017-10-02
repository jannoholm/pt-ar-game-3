if ( obj_gameplay.currentGamePhase == GamePhase.MOVE_TO_POSITIONS && !atPosition ) {
	atPosition = ai_reset_position();
	return;
}

if ( obj_gameplay.currentGamePhase != GamePhase.PLAY ) {
	// If gameplay is not ongoing, don't allow movement
	return;
}

// user select
show_user_select_scroll_cooldown=show_user_select_scroll_cooldown-1;
if (show_user_select && ds_list_size(obj_server_client.user_name_list) > 0) {
	if (show_user_select_id == 0) {
		var show_user_selected=ds_list_find_value(obj_server_client.user_name_list, 0);
		show_user_select_id=show_user_selected.user_id;
		show_user_select_name=show_user_selected.user_name;
	}
	if (show_user_select_scroll_cooldown <=0 && go_move != 0) {
		show_user_select_scroll_cooldown=5;
		
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
			if (show_user_select_pos <= 0) {
				show_user_select_pos=ds_list_size(obj_server_client.user_name_list)-1;
			} else {
				show_user_select_pos=show_user_select_pos-1;
			}
		} else if (go_move < 0) {
			if (show_user_select_pos >= ds_list_size(obj_server_client.user_name_list)-1) {
				show_user_select_pos=0;
			} else {
				show_user_select_pos=show_user_select_pos+1;
			}
		}
		
		// set data of scrolled user
		var show_user_selected = ds_list_find_value(obj_server_client.user_name_list, show_user_select_pos);
		show_user_select_id=show_user_selected.user_id;
		show_user_select_name=show_user_selected.user_name;
	}
	return;
}

// Reset position when game starts
atPosition = false;


var leftWheelPower = 0;
var rightWheelPower = 0;

if (colliding) {
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
}

if (damaged>0) {
    rightWheelPower=rightWheelPower/2;
    leftWheelPower=leftWheelPower/2;
} else if (boost && boost_power>0) {
    boost_power=boost_power-30;
    rightWheelPower=rightWheelPower*2;
    leftWheelPower=leftWheelPower*2;
}

// Apply physical force to tire locations
physics_apply_local_force(rearTireOffsetX, rearLeftTireOffsetY, leftWheelPower*10000, 0);
physics_apply_local_force(rearTireOffsetX, rearRightTireOffsetY, rightWheelPower*10000, 0);

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
		shoot_delay=30;

		var pos_x = x+dcos((-1)*phy_rotation)*120;
		var pos_y = y+dsin(phy_rotation)*100;
		var bullet=instance_create_layer(pos_x, pos_y, "car", obj_bullet);
		bullet.phy_rotation = phy_rotation;
		with(bullet){
			physics_apply_local_force(0, 0, phy_mass*1000, 0);
		}
		ds_list_add(bullets, bullet);
	}

	colliding=0;
}
damaged=damaged-1;
shoot_delay=shoot_delay-1;
boost_power=boost_power+1;

