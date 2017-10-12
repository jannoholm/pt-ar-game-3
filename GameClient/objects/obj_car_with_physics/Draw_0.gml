// draw highlight
if (highlight > 0 
	|| obj_gameplay.currentGamePhase == GamePhase.WAIT_TO_START && ready
	|| obj_gameplay.currentGamePhase == GamePhase.COUNTDOWN_TO_START && ready) {
	draw_sprite_ext(spr_car_highlight, 0, x, y, 1, 1, image_angle, c_white, 1);
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
var pos_x = x;
var pos_y = y+80;
draw_rectangle_colour(pos_x-40, pos_y-7, pos_x+40, pos_y+7, c_green, c_green, c_green, c_green, 0);
draw_rectangle_colour(pos_x-40, pos_y-7, pos_x-40+boost_availability, pos_y+7, c_lime, c_lime, c_lime, c_lime, 0);
draw_rectangle_colour(pos_x-40, pos_y-7, pos_x+40, pos_y+7, c_white, c_white, c_white, c_white, 1);

// draw keyboard enabled
if (keyboard_show) {
	draw_sprite_ext(spr_show_arrowkeys, 0, pos_x, pos_y, 1, 1, 0, c_white, 1);
}

// draw name on car
draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);
draw_set_color(c_white);
draw_text_transformed(x, y, client_name, 1, 1, image_angle);

// draw select name box
if (show_user_select) {
	var offset_x=x+50;
	var offset_y=y+70;
	if (x > 760) {
		var offset_x=x-50;
	}

	draw_sprite_ext(spr_user_select, 0, offset_x, offset_y, 1, 1, 0, c_white, 1);
	draw_set_halign(fa_left);	
	draw_set_font(fnt_textbox);

	draw_set_color(c_gray);
	draw_text_color(offset_x-100, offset_y-17, show_user_select_name_prev, c_white, c_white, c_gray, c_gray, 1);
	draw_text_color(offset_x-100, offset_y+17, show_user_select_name_next, c_gray, c_gray, c_white, c_white, 1);

	draw_set_color(c_black);
	draw_set_font(fnt_textbox);
	draw_text(offset_x-100, offset_y, show_user_select_name);
}

// temp draw of stats at end of game
if (obj_gameplay.currentGamePhase == GamePhase.GAME_END_ANIMATION) {
	var offset_x=x+50;
	var offset_y=y+100;
	if (x > 760) {
		var offset_x=x-50;
	}
	if (y > 600) {
		var offset_y=y-90;
	}
	draw_set_color(c_white);
	draw_set_font(fnt_textbox);
	draw_set_halign(fa_right);
	draw_text(offset_x, offset_y, "goals: " + string(score_goals));
	draw_text(offset_x, offset_y+15, "bullet hits: " + string(score_bullet_hits));
	draw_text(offset_x, offset_y+30, "boost touches: " + string(score_boost_touches));
}

// draw debug info
if (dodraw) {
	draw_set_halign(fa_left);
	draw_set_valign(fa_top);
	draw_set_font(fnt_textbox);
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


