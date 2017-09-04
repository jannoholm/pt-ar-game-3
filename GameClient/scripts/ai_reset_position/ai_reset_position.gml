enum ResetPhase {
	GO_TO_MID,
	ROTATE_AT_MID,
	BACK_UP_TO_START,
	RESET_COMPLETE
}


var currentDistance = distance_to_point(initialPosX, initialPoxY);
var currentAngleDiff = angle_difference(phy_rotation mod 360, initialRotation);

// Verify that car is not already on the spot and is not moving
if ( currentDistance < 10 && -3 <= currentAngleDiff && currentAngleDiff < 3  && phy_speed < 5/room_speed ) {
	currentResetPhase = ResetPhase.RESET_COMPLETE;
	show_debug_message("Reset complete");
	// Reset position complete
	return true;
}

if ( currentResetPhase == ResetPhase.RESET_COMPLETE ) {
	currentResetPhase = ResetPhase.GO_TO_MID;
}

if ( currentResetPhase == ResetPhase.GO_TO_MID ) {

	// If car was on left side of the field initially, then point ahead is positive
	var pointInFrontOfCar = initialPosX < room_width / 2 ? 200 : -200;

	var atPosition = ai_drive_to_point(initialPosX + pointInFrontOfCar, initialPoxY);

	if ( atPosition ) {
		currentResetPhase = ResetPhase.ROTATE_AT_MID;
	}
}

if ( currentResetPhase == ResetPhase.ROTATE_AT_MID ) {

	var rotated = ai_rotate_at_position(initialRotation);

	if ( rotated ) {
		currentResetPhase = ResetPhase.BACK_UP_TO_START;
	}
}

if ( currentResetPhase == ResetPhase.BACK_UP_TO_START ) {

	var distance = point_distance(x, y, initialPosX, initialPoxY);
	
	if (distance_to_point(initialPosX, initialPoxY) < 8) {
		currentResetPhase = ResetPhase.RESET_COMPLETE;
		show_debug_message("Reset complete");
		// Reset position complete
		return true;
	}
	
	// If car was on left side of the field initially, then point ahead is positive
	var fixturePoint = initialPosX < room_width / 2 ? distance/2 : -distance/2;
	
	var curveX = ai_curve(x, initialPosX + fixturePoint, initialPosX, 0.95);
	var curveY = ai_curve(y, initialPoxY, initialPoxY, 0.95);

	var nextPointAngle = point_direction(x, y, curveX, curveY);

	var currentAngleDiff = angle_difference(-phy_rotation mod 360, nextPointAngle);

	if (currentAngleDiff > 0 ) {
		currentAngleDiff -= 180;
	} else {
		currentAngleDiff += 180;
	}


	if(dodraw) {
		show_debug_message("Reseting position:"
			+ "  x=" + string(x)
			+ ", curveX=" + string(curveX)
			+ ", y=" + string(y)
			+ ", curveY=" + string(curveY)
			+ ", distance=" + string(distance)
			+ ", phy_rotation=" + string(-phy_rotation mod 360)
			+ ", nextPointAngle=" + string(nextPointAngle)
			+ ", currentAngleDiff=" + string(currentAngleDiff)
		);
	}

	if(-180 <= currentAngleDiff && currentAngleDiff < -2 ) {

		ai_move_car(0, -0.2);
		if(dodraw) { show_debug_message("Moving car left=" + string(currentAngleDiff)); }
		return false;
	}

	if(2 < currentAngleDiff && currentAngleDiff <= 180 ) {
		ai_move_car(-0.2, 0);
		if(dodraw) { show_debug_message("Moving car right=" + string(currentAngleDiff) ); }
		return false;
	}

	if(dodraw) { show_debug_message("Moving car normally") };

	ai_move_car(-0.3, -0.3);
}

// Resetting position not yet complete
return false;




