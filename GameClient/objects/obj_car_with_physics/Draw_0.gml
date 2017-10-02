draw_self();
if (highlight > 0) {
	draw_sprite_ext(spr_car_highlight, 0, x, y, 1, 1, image_angle, c_white, 1);
}

draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

draw_set_color(c_white);
draw_text_transformed(x, y, client_name, 1, 1, image_angle);

if (show_user_select) {
	draw_sprite_ext(spr_user_select, 0, x+50, y+70, 1, 1, 0, c_white, 1);
	draw_set_font(fnt_textbox);
	draw_set_color(c_black);
	draw_set_halign(fa_left);
	draw_text(x-25, y+70, show_user_select_name);
}

if (dodraw) {
	draw_set_halign(fa_left);
	draw_set_valign(fa_top);

	var dx=40;
	var dy=40;
	
	/*draw_text(dx, dy+0, "colliding: " + string(colliding));
	
	draw_text(dx, dy+0, "velo: " + string(phy_angular_velocity));
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


