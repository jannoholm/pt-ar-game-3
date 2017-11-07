var prevBallInArea = ballInArea;


if ( instance_exists(obj_ball) ) {
	var distance = point_distance( x, y, obj_ball.x, obj_ball.y );
	
	if( distance < senseBallRadius ) {
		ballInArea = true;
	} else {
		ballInArea = false;
	}
}

nrOfCarsInArea = 0;

var r = senseCarsRadius;
if ( collision_ellipse( x-r, y-r, x+r, y+r, obj_playerinit_physics.red1, false, true ) ) {
	nrOfCarsInArea++;
}
if ( collision_ellipse( x-r, y-r, x+r, y+r, obj_playerinit_physics.red2, false, true ) ) {
	nrOfCarsInArea++;	
}
if ( collision_ellipse( x-r, y-r, x+r, y+r, obj_playerinit_physics.blue1, false, true ) ) {
	nrOfCarsInArea++;	
}
if ( collision_ellipse( x-r, y-r, x+r, y+r, obj_playerinit_physics.blue2, false, true ) ) {
	nrOfCarsInArea++;	
}


// If ball and at least 2 cars are in area, then increment the counter, otherwise reset
if ( ballInArea && nrOfCarsInArea > 1 ) {
	blastBallTimer++;
} else {
	blastBallTimer = 0;
}

// Change the ball color to red based on the timer percentage
if ( ballInArea ) {

	// Ignore first two seconds for not confusing players - clamp the value to 0
	var triggerPercentage = clamp((blastBallTimer - 2*room_speed), 0, blastBallTimer) / blastBallTriggerTime;
	with(obj_ball) {
		image_blend = make_color_rgb(255, 255-triggerPercentage*255, 255-triggerPercentage*255);
	}
}

// Reset the color if in previous frame the ball was still in the area
if ( instance_exists(obj_ball) && prevBallInArea && !ballInArea ) {
	with(obj_ball) {
		image_blend = c_white;
	}
}

// Blasting time!
if ( blastBallTimer > blastBallTriggerTime ) {
	blastBallTimer = 0;
	
	show_debug_message("Ball blast trigger time reached");
	
	scr_blast_car_away(obj_playerinit_physics.red1);
	scr_blast_car_away(obj_playerinit_physics.red2);
	scr_blast_car_away(obj_playerinit_physics.blue1);
	scr_blast_car_away(obj_playerinit_physics.blue2);
	
	with(obj_ball) {
		
		// Create large size explosion using built-in particles (and medium one inside for nicer effect)
		effect_create_above(ef_explosion, x, y, 2, c_orange);
		effect_create_above(ef_explosion, x, y, 1, make_color_rgb(176,64,16));
		
		// Play explosion sound
		audio_play_sound(snd_missile_explosion, 5, false);
		
		// Blast the ball towards the middle
		var blastPowerX = x + lengthdir_x(other.blastBallPower, point_direction(x,y,room_width/2,room_height/2));
		var blastPowerY = y + lengthdir_y(other.blastBallPower, point_direction(x,y,room_width/2,room_height/2));
		physics_apply_force(x, y, blastPowerX, blastPowerY);
	}
}