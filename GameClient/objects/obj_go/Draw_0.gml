draw_set_font(fnt_gamewin);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

var size=clamp(lifetime, 0, lifetime_limit/4)/(lifetime_limit/4) + lifetime/lifetime_limit;


draw_text_ext_transformed_color(1920/2, 1080/3, "GO", 0, 500, size, size, 0, c_gray, c_gray, c_gray, c_gray, 0.5)
draw_text_ext_transformed_color(1920/2-1, 1080/3-1, "GO", 0, 500, size, size, 0, c_orange, c_orange, c_orange, c_orange, 0.5)