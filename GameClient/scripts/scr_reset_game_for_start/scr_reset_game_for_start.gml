//with (obj_playerinit_physics.red1) {
with (obj_car_with_physics) {
	damaged=0;
	shoot=false;
	shoot_delay=0;
	shoot_interval=10*room_speed; // every 10s
	boost=false;
	boost_power=6*room_speed;
	boost_max=30*room_speed; // 1.6 sec
	ds_list_clear(bullets);

	// score elements
	score_last_touch=current_time;
	score_goals=0;
	score_bullet_hits=0;
	score_boost_touches=0;
	ready=0;
}
/*
with (obj_playerinit_physics.red2) {
	damaged=0;
	shoot=false;
	shoot_delay=0;
	shoot_interval=10*room_speed; // every 10s
	boost=false;
	boost_power=6*room_speed;
	boost_max=30*room_speed; // 1.6 sec
	ds_list_clear(bullets);

	// score elements
	score_last_touch=current_time;
	score_goals=0;
	score_bullet_hits=0;
	score_boost_touches=0;
	ready=0;
}
with (obj_playerinit_physics.blue1) {
	damaged=0;
	shoot=false;
	shoot_delay=0;
	shoot_interval=10*room_speed; // every 10s
	boost=false;
	boost_power=6*room_speed;
	boost_max=30*room_speed; // 1.6 sec
	ds_list_clear(bullets);

	// score elements
	score_last_touch=current_time;
	score_goals=0;
	score_bullet_hits=0;
	score_boost_touches=0;
	ready=0;
}
with (obj_playerinit_physics.blue2) {
	damaged=0;
	shoot=false;
	shoot_delay=0;
	shoot_interval=10*room_speed; // every 10s
	boost=false;
	boost_power=6*room_speed;
	boost_max=30*room_speed; // 1.6 sec
	ds_list_clear(bullets);

	// score elements
	score_last_touch=current_time;
	score_goals=0;
	score_bullet_hits=0;
	score_boost_touches=0;
	ready=0;
}*/