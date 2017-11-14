// Move car

var resetPositionOffsetX;
var resetPositionOffsetY = initialPoxY;
var roomWidthHalf = room_width / 2;

if ( initialPosX < roomWidthHalf ) {
	resetPositionOffsetX = initialPosX - 70;
} else {
	resetPositionOffsetX = initialPosX + 70;
}

var currentDistance = distance_to_point(resetPositionOffsetX, resetPositionOffsetY);
var currentAngleDiff = angle_difference(phy_rotation mod 360, initialRotation);

// Verify that car is not already on the spot and is not moving
if ( currentDistance < 8 && phy_speed < 30/room_speed ) {
	if ( -5 <= currentAngleDiff && currentAngleDiff < 5 ) {
		currentResetPhase = ResetPhase.RESET_COMPLETE;
		show_debug_message("Reset complete");
		// Reset position complete
		return true;
	} else {
		show_debug_message("Car reset angle too wide, going back to mid");
		currentResetPhase = ResetPhase.GO_TO_MID
	}
} else if ( currentResetPhase == ResetPhase.RESET_COMPLETE ) {
	// Car was probably nudged by somone, restart the reset cycle
	currentResetPhase = ResetPhase.GO_TO_MID
}

if ( currentResetPhase == ResetPhase.RESET_COMPLETE ) {
	currentResetPhase = ResetPhase.GO_TO_MID;
}

if ( currentResetPhase == ResetPhase.GO_TO_MID ) {

	// If car was on left side of the field initially, then point ahead is positive
	var pointInFrontOfCar = resetPositionOffsetX < room_width / 2 ? 300 : -300;

	var atPosition = ai_drive_to_point(resetPositionOffsetX + pointInFrontOfCar, resetPositionOffsetY);

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

	var distance = point_distance(x, y, resetPositionOffsetX, resetPositionOffsetY) * 1.0;
	
	// Need to stop the car if point is close enough
	if (distance_to_point(resetPositionOffsetX, resetPositionOffsetY) < 12) {
		// Return false and let the main check finish the reset logic
		return false;
	}
	
	// If car was on left side of the field initially, then point ahead is positive
	var roomWidthHalf = room_width / 2;
	var fixturePoint;
	if ( resetPositionOffsetX < roomWidthHalf ) {
		fixturePoint = distance / 2;
	} else {
		fixturePoint = distance / -2;
	}
	
	var curveX = ai_curve(x, resetPositionOffsetX + fixturePoint, resetPositionOffsetX, 0.95);
	var curveY = ai_curve(y, resetPositionOffsetY, resetPositionOffsetY, 0.95);

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




