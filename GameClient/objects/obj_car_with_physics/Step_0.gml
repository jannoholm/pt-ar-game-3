var leftWheelPower = 0;
var rightWheelPower = 0;

if( go_forward ) {
	leftWheelPower = 1;
	rightWheelPower = 1;
	
	if( go_left ) {
		rightWheelPower = 0.25;
	}

	if( go_right ) {
		leftWheelPower = 0.25;
	}
}

if( go_backward ) {
	leftWheelPower = -1;
	rightWheelPower = -1;
	
	if( go_left ) {
		rightWheelPower = -0.25;
	}

	if( go_right ) {
		leftWheelPower = -0.25;
	}
}

scr_car_move(leftWheelPower, rightWheelPower);
