draw_self();

draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

draw_text_transformed(x, y, client_name, 1, 1, image_angle);
//draw_text(x,y,client_name);

if (dodraw) {
	draw_text(0, 0, "Dire: " + string(tire_dire));
	draw_text(0, 20, "Spd: " + string(phy_speed));
	draw_text(0, 40, "Mass: " + string(phy_mass));
	draw_text(0, 60, "Frc: " + string((-1*phy_mass*phy_speed_x)/10));
}


