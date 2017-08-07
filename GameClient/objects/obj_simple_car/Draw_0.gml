draw_self();

draw_set_font(fnt_textbox);
draw_set_halign(fa_center);
draw_set_valign(fa_center);

draw_text_transformed(x, y, client_name, 1, 1, image_angle);
//draw_text(x,y,client_name);

