draw_set_font(fnt_gamewin);
draw_set_color(c_orange);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

var size=clamp(lifetime, 0, lifetime_limit/4)/(lifetime_limit/4) + lifetime/lifetime_limit;
draw_text_transformed(1920/2, 1080/3, "SUDDEN DEATH ROUND", size, size, 0);