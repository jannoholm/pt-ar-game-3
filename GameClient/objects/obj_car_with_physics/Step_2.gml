/// @description Update car object's location

// Update rear tire locations
var tireAngle, tireDistance, tireX, tireY;

// Calculate the coordinates of rear right tire
tireAngle = point_direction(0, 0, rearTireOffsetX, rearRightTireOffsetY);
tireDistance = point_distance(0, 0, rearTireOffsetX, rearRightTireOffsetY);
tireX = x + lengthdir_x(tireDistance, image_angle + tireAngle);
tireY = y + lengthdir_y(tireDistance, image_angle + tireAngle);

with ( rearLeftTire ) {
	x = tireX;
	y = tireY;
}

// Calculate the coordinates of rear left tire
tireAngle = point_direction(0, 0, rearTireOffsetX, rearLeftTireOffsetY);
tireDistance = point_distance(0, 0, rearTireOffsetX, rearLeftTireOffsetY);
tireX = x + lengthdir_x(tireDistance, image_angle + tireAngle);
tireY = y + lengthdir_y(tireDistance, image_angle + tireAngle);

with ( rearRightTire ) {
	x = tireX;
	y = tireY;
}