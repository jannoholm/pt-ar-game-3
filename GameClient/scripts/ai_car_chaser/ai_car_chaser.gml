var ballDistance = distance_to_object(obj_ball);
var ballDirection = point_direction(x, y, obj_ball.x, obj_ball.y);
var ballAngle = angle_difference(-phy_rotation, ballDirection);

var aiLogicResult;

// Ball stright ahead
if( -10 <= ballAngle && ballAngle <= 10 ) {

	ai_move_car(1, 1);
	return;
}

// Ball behind on right
if( 60 <= ballAngle && ballAngle <= 180 ) {
	ai_move_car(-1, 0.25);
	return;
}

// Ball behind on left
if( -180 <= ballAngle && ballAngle <= -60 ) {
	ai_move_car(0.25, -1);
	return;
}

// Ball to close, can't turn
if( ballDistance < 5 ){
	ai_move_car(-1, -1);
	return;
}

// Ball on right
if( 10 < ballAngle && ballAngle < 60 ) {
	ai_move_car(0, 1);
	return;
}

// Ball on left
if( -60 < ballAngle && ballAngle < -10 ) {
	ai_move_car(1, 0);
	return;
}