var parkTargetX = argument0;
var parkTargetY = argument1;

var targetDistance = distance_to_point(parkTargetX, parkTargetY);
var targetDirection = point_direction(x, y, parkTargetX, parkTargetY);
var targetAngle = angle_difference(-phy_rotation, targetDirection);

if( targetDistance < 10 ) {
	show_debug_message("Car driven to point X=" + string(parkTargetX) + " Y=" + string(parkTargetY) );
	return true;
}

// Target stright ahead
if( -10 <= targetAngle && targetAngle <= 10 ) {

	ai_move_car(1, 1);
	return false;
}

// Target behind on right
if( 60 <= targetAngle && targetAngle <= 180 ) {
	ai_move_car(-1, 0.5);
	return false;
}

// Target behind on left
if( -180 <= targetAngle && targetAngle <= -60 ) {
	ai_move_car(0.5, -1);
	return false;
}

// Target on right
if( 10 < targetAngle && targetAngle < 60 ) {
	ai_move_car(0, 1);
	return false;
}

// Target on left
if( -60 < targetAngle && targetAngle < -10 ) {
	ai_move_car(1, 0);
	return false;
}

