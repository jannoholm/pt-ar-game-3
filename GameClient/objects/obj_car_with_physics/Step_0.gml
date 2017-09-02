var leftWheelPower = 0;
var rightWheelPower = 0;

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
