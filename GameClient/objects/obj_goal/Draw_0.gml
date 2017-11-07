if (show_goal > 0) {
	draw_set_halign(fa_center);
	draw_set_valign(fa_center);
	draw_set_font(fnt_gamewin);
	
	//draw_text(1920/2, 1080/2, "GOAL for team " + team);
	draw_text_color(1920/2, 1080/2, string_lower("goal by " + client_name), c_gray, c_gray, c_gray, c_gray, 0.5);
	draw_text_color(1920/2-1, 1080/2-1, string_lower("goal by " + client_name), c_orange, c_orange, c_orange, c_orange, 0.5);
}