if (score_last_touch_interval < current_time-500) {
	score_last_touch_interval=current_time;
	score_ball_touches=score_ball_touches+1;
	if (boost) {
		score_boost_touches=score_boost_touches+1;
	}
}

score_last_touch=current_time;