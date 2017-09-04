if ( obj_gameplay.currentGamePhase == GamePhase.MOVE_TO_POSITIONS && !atPosition ) {
	atPosition = ai_reset_position();
	return;
}

if ( obj_gameplay.currentGamePhase != GamePhase.PLAY ) {
	// If gameplay is not ongoing, don't allow movement
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

		var bullet=instance_create_layer(x+dcos((-1)*phy_rotation)*120,y+dsin(phy_rotation)*100,"car", obj_bullet);
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
