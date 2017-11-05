var carToCheck = argument0;

with ( carToCheck ) {
	var ballDistance = distance_to_object(obj_ball);
	
	if ( ballDistance > 50 ) {
		// Ball too far away, no action
		return;
	}
}

var ballDirection = point_direction(carToCheck.x, carToCheck.y, obj_ball.x, obj_ball.y);
var ballAngle = angle_difference(-carToCheck.phy_rotation, ballDirection);


// Ball stright ahead
if( -10 <= ballAngle && ballAngle <= 10 ) {
	with (carToCheck) {
		damaged = room_speed*2; // 2 sec
		damage_direction = -1;
		damage_turn = choose(-1, 1);
	}
	return;
}

// Ball behind on right
if( 60 <= ballAngle && ballAngle <= 180 ) {
	with (carToCheck) {
		damaged = room_speed*1; // 1 sec
		damage_direction = 1;
		damage_turn = 1
	}
	return;
}

// Ball behind on left
if( -180 <= ballAngle && ballAngle <= -60 ) {
	with (carToCheck) {
		damaged = room_speed*2; // 2 sec
		damage_direction = 1;
		damage_turn = -1;
	}
	return;
}

// Ball on right
if( 10 < ballAngle && ballAngle < 60 ) {
	with (carToCheck) {
		damaged = room_speed*2; // 2 sec
		damage_direction = -1;
		damage_turn = 1;
	}
	return;
}

// Ball on left
if( -60 < ballAngle && ballAngle < -10 ) {
	with (carToCheck) {
		damaged = room_speed*2; // 2 sec
		damage_direction = -1;
		damage_turn = -1;
	}
	return;
}