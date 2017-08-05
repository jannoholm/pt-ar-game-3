draw_self();

if (dodraw) {
	draw_text(0, 0, "Dire: " + string(tire_dire));
	draw_text(0, 20, "Spd: " + string(phy_speed));
	draw_text(0, 40, "Mass: " + string(phy_mass));
	draw_text(0, 60, "Frc: " + string((-1*phy_mass*phy_speed_x)/10));
}


