var leftWheelPower = 0;
var rightWheelPower = 0;

if( go_forward ) {
	if( go_left && go_right ) {
		leftWheelPower = 1;
		rightWheelPower = 1;
	} else if( go_left ) {
		leftWheelPower = 1.7;
		rightWheelPower = 0.3;
	} else if( go_right ) {
		leftWheelPower = 0.3;
		rightWheelPower = 1.7;
	} else {
		leftWheelPower = 1;
		rightWheelPower = 1;
	}
}

if( go_backward ) {
	if( go_left && go_right ) {
		leftWheelPower = -1;
		rightWheelPower = -1;
	} else if( go_left ) {
		leftWheelPower = -1.3;
		rightWheelPower = -0.7;
	} else if( go_right ) {
		leftWheelPower = -0.7;
		rightWheelPower = -1.3;
	} else {
		leftWheelPower = -1;
		rightWheelPower = -1;
	}
}

with(rearLeftTire){
	physics_apply_local_force(0, 0, leftWheelPower*10000, 0);
}

with(rearRightTire){
	physics_apply_local_force(0, 0, rightWheelPower*10000, 0);
}
