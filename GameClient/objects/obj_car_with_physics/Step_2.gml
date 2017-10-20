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

var boostFlameAngle, boostFlameDistance, boostFlameX, boostFlameY;

// Update boost flame 
if ( boost_flame_animated ) {
	boostFlameAngle = point_direction(0, 0, boostFlameOffsetX, 0);
	boostFlameDistance = point_distance(0, 0, boostFlameOffsetX, 0);
	boostFlameX = x + lengthdir_x(boostFlameDistance, image_angle + boostFlameAngle);
	boostFlameY = y + lengthdir_y(boostFlameDistance, image_angle + boostFlameAngle);	
	with ( boostFlame ) {
		image_angle = other.image_angle;
		x = boostFlameX;
		y = boostFlameY;
	}
}
