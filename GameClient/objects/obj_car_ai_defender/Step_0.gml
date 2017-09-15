if ( obj_gameplay.currentGamePhase == GamePhase.MOVE_TO_POSITIONS && !atPosition ) {
	atPosition = ai_reset_position();
	return;
}

if ( obj_gameplay.currentGamePhase != GamePhase.PLAY ) {
	// If gameplay is not ongoing, don't allow movement
	return;
}

// Reset position when game starts
atPosition = false;


if ( teamColor = TeamColor.RED ) {

	var ballDirection = point_direction(0, room_height/2, obj_ball.x, obj_ball.y);
	var defencePointX = 0 + lengthdir_x(250, ballDirection);
	var defencePointY = room_height/2 + lengthdir_y(250, ballDirection);

	ai_drive_to_point(defencePointX, defencePointY)

} else {

	var ballDirection = point_direction(room_width, room_height/2, obj_ball.x, obj_ball.y);
	var defencePointX = room_width + lengthdir_x(250, ballDirection);
	var defencePointY = room_height/2 + lengthdir_y(250, ballDirection);

	ai_drive_to_point(defencePointX, defencePointY)
}
