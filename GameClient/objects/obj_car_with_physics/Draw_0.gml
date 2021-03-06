// draw highlight
if (highlight > 0 
	|| obj_gameplay.currentGamePhase == GamePhase.WAIT_TO_START && ready
	|| obj_gameplay.currentGamePhase == GamePhase.COUNTDOWN_TO_START && ready
	|| obj_gameplay.currentGamePhase == GamePhase.GAME_END_ANIMATION && ready
	|| obj_gameplay.currentGamePhase == GamePhase.PLAY && obj_gameplay.currentCarPhase == CarPhase.WAIT_TO_START && ready
	|| obj_gameplay.currentGamePhase == GamePhase.PLAY && obj_gameplay.currentCarPhase == CarPhase.COUNTDOWN_TO_START && ready) {
	if (teamColor == TeamColor.RED) {
		draw_sprite_ext(spr_car_highlight_red, 0, x, y, 1, 1, image_angle, c_white, 1);
	} else if (teamColor == TeamColor.BLUE) {
		draw_sprite_ext(spr_car_highlight_blue, 0, x, y, 1, 1, image_angle, c_white, 1);
	}
}

// draw car 
draw_self();

// draw bullet to shoot
if (shoot_delay < 0) {
	var pos_x = x+dcos((-1)*phy_rotation)*sprite_width/2;
	var pos_y = y+dsin(phy_rotation)*sprite_width/2;

	draw_sprite_ext(spr_bullet, 0, pos_x, pos_y, 1, 1, (-1)*phy_rotation, c_white, 1);
}

// draw boost availability
var boost_availability = clamp(boost_power, 0, boost_max)/boost_max*80;
var pos_x = x-80;
var pos_y = y;
var dire=-90;
var correction=7;
if (teamColor == TeamColor.BLUE) {
	pos_x = x+80;
	dire=90;
	correction=-6;
}
draw_rectangle_colour(pos_x-7, pos_y-40, pos_x+7, pos_y+40, c_green, c_green, c_green, c_green, 0);
if (teamColor == TeamColor.RED) {
	draw_rectangle_colour(pos_x-7, pos_y-40, pos_x+7, pos_y-40+boost_availability, c_lime, c_lime, c_lime, c_lime, 0);
} else if (teamColor == TeamColor.BLUE) {
	draw_rectangle_colour(pos_x-7, pos_y+41, pos_x+7, pos_y+40-boost_availability, c_lime, c_lime, c_lime, c_lime, 0);
}
draw_rectangle_colour(pos_x-7, pos_y-40, pos_x+7, pos_y+40, c_white, c_white, c_white, c_white, 1);

// boost and name on car
draw_set_font(fnt_usertext);
draw_set_halign(fa_center);
draw_set_valign(fa_center);
draw_set_color(c_white);
draw_text_ext_transformed(pos_x+correction, pos_y, "BOOST", 0, 200, 1, 1, dire);
draw_text_transformed(x, y, client_name, 1, 1, image_angle);

// draw keyboard enabled
if (keyboard_show) {
	draw_sprite_ext(spr_show_arrowkeys, 0, x, y+80, 1, 1, 0, c_white, 1);
}

// draw select name box
if (show_user_select && !( obj_gameplay.currentGamePhase == GamePhase.PLAY || obj_gameplay.currentGamePhase == GamePhase.SUDDEN_DEATH )) {
	var offset_x=x+200;
	var offset_y=y;
	var offset_angle=270;
	var offset_text=-100;
	var offset_scroll=-20;
	if (teamColor == TeamColor.BLUE) {
		offset_x=x-200;
		offset_angle=90;
		offset_text=100;
		offset_scroll=20;
	}

	//draw_sprite_ext(spr_user_select, 0, offset_x, offset_y, 1, 1, offset_angle, c_white, 1);
	//draw_sprite(spr_playerselect, 1, offset_x+offset_scroll, offset_y+offset_text);
	
	draw_set_halign(fa_left);	
	draw_set_font(fnt_usertext);

	draw_set_color(c_gray);
	draw_text_transformed(offset_x+(-1)*2*offset_scroll, offset_y+offset_text, show_user_select_name_prev2, 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-1)*1*offset_scroll, offset_y+offset_text, show_user_select_name_prev1, 1, 1, offset_angle);
	draw_text_transformed(offset_x+1*offset_scroll, offset_y+offset_text, show_user_select_name_next1, 1, 1, offset_angle);
	draw_text_transformed(offset_x+2*offset_scroll, offset_y+offset_text, show_user_select_name_next2, 1, 1, offset_angle);
	draw_text_transformed(offset_x, offset_y+offset_text, show_user_select_name, 1, 1, offset_angle);

	draw_set_color(c_orange);
	draw_text_transformed(offset_x-1+(-1)*2*offset_scroll, offset_y-1+offset_text, show_user_select_name_prev2, 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-1)*1*offset_scroll, offset_y-1+offset_text, show_user_select_name_prev1, 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+1*offset_scroll, offset_y-1+offset_text, show_user_select_name_next1, 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+2*offset_scroll, offset_y-1+offset_text, show_user_select_name_next2, 1, 1, offset_angle);
	draw_set_color(c_white);
	draw_text_transformed(offset_x-1, offset_y-1+offset_text, show_user_select_name, 1, 1, offset_angle);
} else
// temp draw of stats at end of game
if (obj_gameplay.currentGamePhase == GamePhase.GAME_END_ANIMATION) {
	var offset_x=x+200;
	var offset_y=y;
	var offset_angle=270;
	var offset_text=+100;
	var offset_scroll=-20;
	if (teamColor == TeamColor.BLUE) {
		offset_x=x-200;
		offset_angle=90;
		offset_text=-100;
		offset_scroll=20;
	}

	draw_set_color(c_gray);
	draw_set_font(fnt_usertext);
	draw_set_halign(fa_right);
	draw_text_transformed(offset_x+(-4)*offset_scroll, offset_y+offset_text, "GOALS: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-3)*offset_scroll, offset_y+offset_text, "BULLET HITS: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-2)*offset_scroll, offset_y+offset_text, "BALL TOUCHES: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-1)*offset_scroll, offset_y+offset_text, "BOOST TOUCHES: ", 1, 1, offset_angle);
	if (score_gamescore!=-1000000) {
		draw_text_transformed(offset_x+( 0)*offset_scroll, offset_y+offset_text, "GAME SCORE: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x+( 1)*offset_scroll, offset_y+offset_text, "RATING POINTS: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text, "LEADERBOARD POSITION: ", 1, 1, offset_angle);	
	}
	draw_text_transformed(offset_x+( 4)*offset_scroll, offset_y+offset_text, "PRESS CIRCLE TO CONTINUE", 1, 1, offset_angle);
	
	draw_set_color(c_orange);
	draw_text_transformed(offset_x-1+(-4)*offset_scroll, offset_y-1+offset_text, "GOALS: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-3)*offset_scroll, offset_y-1+offset_text, "BULLET HITS: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-2)*offset_scroll, offset_y-1+offset_text, "BALL TOUCHES: ", 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-1)*offset_scroll, offset_y-1+offset_text, "BOOST TOUCHES: ", 1, 1, offset_angle);
	if (score_gamescore!=-1000000) {
		draw_text_transformed(offset_x-1+( 0)*offset_scroll, offset_y-1+offset_text, "GAME SCORE: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x-1+( 1)*offset_scroll, offset_y-1+offset_text, "RATING POINTS: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text, "LEADERBOARD POSITION: ", 1, 1, offset_angle);	
	}
	draw_set_color(c_white);
	draw_text_transformed(offset_x-1+( 4)*offset_scroll, offset_y-1+offset_text, "PRESS CIRCLE TO CONTINUE", 1, 1, offset_angle);

	// todo
	draw_set_color(c_gray);
	draw_set_halign(fa_left);
	draw_text_transformed(offset_x+(-4)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_goals), 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-3)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_bullet_hits), 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-2)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_ball_touches), 1, 1, offset_angle);
	draw_text_transformed(offset_x+(-1)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_boost_touches), 1, 1, offset_angle);
	if (score_gamescore!=-1000000) {
		draw_text_transformed(offset_x+( 0)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_gamescore), 1, 1, offset_angle);
		draw_text_transformed(offset_x+( 1)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_eloRating)+" "+string(score_eloRatingDiff), 1, 1, offset_angle);
		if (score_leaderboardPosition > 0) {
			draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text+offset_text/10, string(score_leaderboardPosition)+" "+string(score_leaderboardPositionDiff), 1, 1, offset_angle);	
		} else {
			draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text+offset_text/10, "play more", 1, 1, offset_angle);	
		}
	}
	
	draw_set_color(c_orange);
	draw_text_transformed(offset_x-1+(-4)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_goals), 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-3)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_bullet_hits), 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-2)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_ball_touches), 1, 1, offset_angle);
	draw_text_transformed(offset_x-1+(-1)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_boost_touches), 1, 1, offset_angle);
	if (score_gamescore!=-1000000) {
		draw_text_transformed(offset_x-1+( 0)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_gamescore), 1, 1, offset_angle);
		draw_text_transformed(offset_x-1+( 1)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_eloRating)+" "+string(score_eloRatingDiff), 1, 1, offset_angle);
		if (score_leaderboardPosition > 0) {
			draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(score_leaderboardPosition)+" "+string(score_leaderboardPositionDiff), 1, 1, offset_angle);	
		} else {
			draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text+offset_text/10, "play more", 1, 1, offset_angle);	
		}
	}

} else if (!ready &&
		(obj_gameplay.currentGamePhase == GamePhase.WAIT_TO_START /*||
		obj_gameplay.currentGamePhase == GamePhase.PLAY && obj_gameplay.currentCarPhase == CarPhase.WAIT_TO_START*/) ) {
	/*var offset_x=x+200;
	var offset_y=y;
	var offset_angle=270;
	var offset_text=-100;
	var offset_scroll=-20;
	if (teamColor == TeamColor.BLUE) {
		offset_x=x-200;
		offset_angle=90;
		offset_text=100;
		offset_scroll=20;
	}*/
	var offset_x=x+200;
	var offset_y=y;
	var offset_angle=270;
	var offset_text=+100;
	var offset_scroll=-20;
	if (teamColor == TeamColor.BLUE) {
		offset_x=x-200;
		offset_angle=90;
		offset_text=-100;
		offset_scroll=20;
	}

	var text_to_show="PRESS SQUARE TO SELECT NAME";
	if (show_user_select_id!=0) {
		text_to_show="PRESS CIRCLE TO START";
	}
	draw_set_halign(fa_right);	
	draw_set_font(fnt_usertext);
	draw_set_color(c_gray);
	draw_text_transformed(offset_x+4*offset_scroll, offset_y+offset_text, text_to_show, 1, 1, offset_angle);
	draw_set_color(c_white);
	draw_text_transformed(offset_x+4*offset_scroll-1, offset_y+offset_text-1, text_to_show, 1, 1, offset_angle);

	if (show_user_select_id!=0) {
		draw_set_color(c_gray);
		draw_set_font(fnt_usertext);
		draw_set_halign(fa_right);
		draw_text_transformed(offset_x+( 1)*offset_scroll, offset_y+offset_text, "RATING POINTS: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text, "LEADERBOARD POSITION: ", 1, 1, offset_angle);	
	
		draw_set_color(c_orange);
		draw_text_transformed(offset_x-1+( 1)*offset_scroll, offset_y-1+offset_text, "RATING POINTS: ", 1, 1, offset_angle);
		draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text, "LEADERBOARD POSITION: ", 1, 1, offset_angle);	

		// todo
		draw_set_color(c_gray);
		draw_set_halign(fa_left);
		draw_text_transformed(offset_x+( 1)*offset_scroll, offset_y+offset_text+offset_text/10, string(show_user_select_elo), 1, 1, offset_angle);
		if (show_user_select_leaderboard_pos > 0) {
			draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text+offset_text/10, string(show_user_select_leaderboard_pos), 1, 1, offset_angle);	
		} else {
			draw_text_transformed(offset_x+( 2)*offset_scroll, offset_y+offset_text+offset_text/10, "play more", 1, 1, offset_angle);	
		}
	
		draw_set_color(c_orange);
		draw_text_transformed(offset_x-1+( 1)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(show_user_select_elo), 1, 1, offset_angle);
		if (show_user_select_leaderboard_pos > 0) {
			draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text+offset_text/10, string(show_user_select_leaderboard_pos), 1, 1, offset_angle);	
		} else {
			draw_text_transformed(offset_x-1+( 2)*offset_scroll, offset_y-1+offset_text+offset_text/10, "play more", 1, 1, offset_angle);	
		}
	}
}


// draw debug info
if (dodraw) {
	draw_set_halign(fa_left);
	draw_set_valign(fa_top);
	draw_set_font(fnt_usertext);
	draw_set_color(c_white);

	var dx=40;
	var dy=40;
	
	draw_text(dx, dy+0, "fps: " + string(fps));
	//draw_text(dx, dy+20, "score_last_touch: " + string(score_last_touch));
	//draw_text(dx, dy+40, "score_goals: " + string(score_goals));
	//draw_text(dx, dy+60, "score_bullet_hits: " + string(score_bullet_hits));
	//draw_text(dx, dy+80, "score_boost_touches: " + string(score_boost_touches));

	/*draw_text(dx, dy+0, "velo: " + string(phy_angular_velocity));
	draw_text(dx, dy+20, "angdamp: " + string(phy_angular_damping));
	draw_text(dx, dy+40, "velx: " + string(phy_linear_velocity_x));
	draw_text(dx, dy+60, "vely: " + string(phy_linear_velocity_y));
	draw_text(dx, dy+80, "lindamp: " + string(phy_linear_damping));
	draw_text(dx, dy+100, "spdx: " + string(phy_speed_x));
	draw_text(dx, dy+120, "spdy: " + string(phy_speed_y));
	draw_text(dx, dy+140, "posx: " + string(phy_position_x));
	draw_text(dx, dy+160, "posy: " + string(phy_position_y));
	draw_text(dx, dy+180, "prex: " + string(phy_position_xprevious));
	draw_text(dx, dy+200, "prey: " + string(phy_position_yprevious));
	draw_text(dx, dy+220, "rota: " + string(phy_rotation));
	draw_text(dx, dy+240, "frot: " + string(phy_fixed_rotation));
	*/
}


