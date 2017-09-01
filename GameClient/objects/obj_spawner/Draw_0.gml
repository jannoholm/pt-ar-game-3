
var Text = "click on the screen to create a random instance of a trail drawing object";

draw_set_colour(c_black)
draw_rectangle(8,8,10+string_width(string_hash_to_newline(Text)),10+string_height(string_hash_to_newline(Text)),0)

draw_set_colour(c_white)
draw_text(10,10,string_hash_to_newline(Text));


