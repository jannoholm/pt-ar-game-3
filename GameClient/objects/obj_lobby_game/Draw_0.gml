draw_self();

draw_set_font(fnt_gamelist);
draw_set_halign(fa_left);
draw_set_valign(fa_center);

if (is_mouse_over) {
	draw_set_colour(c_white);
} else {
	draw_set_colour(c_gray);
}

draw_text(x+50, y, name);

draw_set_font(fnt_gamelist);
draw_text(x+1500, y, "Free: " + string(free_positions) + "/" + string(total_players));
if (ai_type != "") {
	draw_text(x+1600, y, "("+ai_type+")");
}

draw_set_halign(fa_left);
draw_set_valign(fa_top);
draw_set_colour(c_white);