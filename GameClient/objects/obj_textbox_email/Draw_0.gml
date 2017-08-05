draw_self();
draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

if (txt=="") {
	draw_text(x, y, "EMAIL");
} else {
	if (blink==false || selected==false) {
		draw_text(x, y, txt);
	} else {
		draw_text(x, y, txt+"|");
	}
}

draw_set_halign(fa_left);
draw_set_valign(fa_top);
