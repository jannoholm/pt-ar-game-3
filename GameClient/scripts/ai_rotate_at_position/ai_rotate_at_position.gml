// Input parmater for desired angle
var desiredAngle = argument0;

var currentAngleDiff = angle_difference(phy_rotation mod 360, desiredAngle);

// Calculate the next step angle taking into account current angular velocity
// For some reason, the phy_rotation can go beyond 180
var nextStepAngle = phy_rotation mod 360 + phy_angular_velocity/room_speed;

// The difference between current and next step angle
// No idea why phy_rotation does not need to be negative
var nestStepAngleDiff = angle_difference(nextStepAngle, desiredAngle);

// PID algorithm, without I

// Porpotional, also known as error
var pidP = nestStepAngleDiff;

// Ignoreing I part
var pidI = 0;

// Derivative, currentError - previousError
var pidD = nestStepAngleDiff - currentAngleDiff;

// For finetuning
constantP = 0.008;
constantI = 0;
constantD = 0.008;

var pidValue = constantP * pidP + constantI * pidI + constantD * pidD;

var wheelPower = clamp(pidValue, -0.5, 0.5);

if ( dodraw ) {
	show_debug_message(
		"desiredAngle=" + string(desiredAngle)
		 + "; currentAngleDiff=" + string(currentAngleDiff)
		 + "; nextStepAngle=" + string(nextStepAngle)
		 + "; desiredAngleDiff=" + string(nestStepAngleDiff)
		 + "; pidValue=" + string(pidValue)
		 + "; wheelPower=" + string(wheelPower)
		 )
}

if(-2 <= currentAngleDiff && currentAngleDiff < 2 ) {
	show_debug_message("Rotated to desired angle = " + string( desiredAngle) );
	return true;
} else {
	ai_move_car(wheelPower, -wheelPower);
	return false;
}


