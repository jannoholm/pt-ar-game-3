
// Draw tire trails
with ( rearLeftTire ) {
	scr_draw_trail(8,8,0,-1,0,1);
}

with ( rearRightTire ) {
	scr_draw_trail(8,8,0,-1,0,1);
}

draw_self();

draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

draw_text_transformed(x, y, client_name, 1, 1, image_angle);

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


