draw_self();

draw_set_font(fnt_gamelist);
draw_set_halign(fa_left);
draw_set_valign(fa_center);

if (is_mouse_over) {
	draw_set_colour(c_white);
} else {
	draw_set_colour(c_gray);
}

draw_text(x+50, y, "Host: " + name);

draw_set_font(fnt_gamelist);
draw_text(x+1000, y, "Free: " + string(free_positions) + "/" + string(total_players));
if (ai_type != "") {
	draw_text(x+1100, y, "("+ai_type+")");
}

draw_set_halign(fa_left);
draw_set_valign(fa_top);
draw_set_colour(c_white);