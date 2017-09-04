goal=goal+1;
show_goal=120;

instance_destroy(obj_ball);

with ( obj_gameplay ) {
	currentGamePhase = GamePhase.WAIT_TO_START;
}
