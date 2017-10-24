
var car = argument0;
var spillAnimation = argument1;

if (car.remote_control == false) {
	if ( collision_ellipse( x-45, y-15, x+45, y+20, car, false, true ) ) {
		car.colliding = true;

		if ( !spillAnimation ) {
			// Show spill animation only once when colliding
			spillAnimation = true;
			
			var oilSplashAngle, oilSplashDistance
			var oilSplashX, oilSplashY, oilSplashDirection;

			var oilSpillDirection = point_direction(car.x, car.y, x, y);
			// This function will return the smallest angle difference between two angles as a value between -180 and 180 degrees
			// (where a positive angle is anti-clockwise and a negative angle clockwise).
			var oilSpillAngle = angle_difference(-car.phy_rotation, oilSpillDirection);
			
			var wheelOffset = 40;
			
			if ( -180 <= oilSpillAngle &&  oilSpillAngle <= -90 ) {
				// Oilspill back left
				oilSplashAngle = point_direction(0, 0, -wheelOffset, -wheelOffset);
				oilSplashDistance = point_distance(0, 0, -wheelOffset, -wheelOffset);
				oilSplashX = car.x + lengthdir_x(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashY = car.y + lengthdir_y(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashDirection = -car.phy_rotation mod 360;
				
				show_debug_message("Oil splash back left: " + string(oilSpillAngle) + " " + string(car.phy_rotation) + " " + string (oilSplashDirection));
				
			} else if ( -90 <= oilSpillAngle &&  oilSpillAngle <= 0 ) {
				// Oilspill front left
				oilSplashAngle = point_direction(0, 0, wheelOffset, -wheelOffset);
				oilSplashDistance = point_distance(0, 0, wheelOffset, -wheelOffset);
				oilSplashX = car.x + lengthdir_x(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashY = car.y + lengthdir_y(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashDirection = -car.phy_rotation mod 360;
				
				show_debug_message("Oil splash front left: " + string(oilSpillAngle) + " " + string(car.phy_rotation) + " " + string (oilSplashDirection));
				
			} else if ( 0 <= oilSpillAngle &&  oilSpillAngle <= 90 ) {
				// Oilspill front right
				oilSplashAngle = point_direction(0, 0, wheelOffset, wheelOffset);
				oilSplashDistance = point_distance(0, 0, wheelOffset, wheelOffset);
				oilSplashX = car.x + lengthdir_x(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashY = car.y + lengthdir_y(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashDirection = -car.phy_rotation mod 360 + 180; // Right wheels, flip the image
				
				show_debug_message("Oil splash front right: " + string(oilSpillAngle) + " " + string(car.phy_rotation) + " " + string (oilSplashDirection));
				
			} else if ( 90 <= oilSpillAngle &&  oilSpillAngle <= 180 ) {
				// Oilspill back right
				oilSplashAngle = point_direction(0, 0, -wheelOffset, wheelOffset);
				oilSplashDistance = point_distance(0, 0, -wheelOffset, wheelOffset);
				oilSplashX = car.x + lengthdir_x(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashY = car.y + lengthdir_y(oilSplashDistance, car.image_angle + oilSplashAngle);
				oilSplashDirection = -car.phy_rotation mod 360 + 180; // Right wheels, flip the image
				
				show_debug_message("Oil splash back right: " + string(oilSpillAngle) + " " + string(car.phy_rotation) + " " + string (oilSplashDirection));
				
			} else {
				show_debug_message("Something wrong with the angle: " + string(oilSpillAngle));
				spillAnimation = false;
				return spillAnimation;
			}

			var oilSpillAnimation = instance_create_layer(oilSplashX, oilSplashY, "car", obj_oil_splash);
			with ( oilSpillAnimation ) {
				image_angle = oilSplashDirection;
			}
			
		}
	} else {
		// Car has exited the area, show animation next time again
		spillAnimation = false;	
	}	
}

return spillAnimation;