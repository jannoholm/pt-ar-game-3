green1 = instance_create_layer(100, 306, "car", obj_car);
with (green1) {
	car_position=1;
	KEY_FORWARD=ord("W");
	KEY_BACKWARD=ord("S");
	KEY_LEFT=ord("A");
	KEY_RIGHT=ord("D");
	image_blend = make_color_rgb(55, 229, 119);
	phy_rotation = -10;
}

green2 = instance_create_layer(100, 446, "car", obj_car);
with (green2) {
	car_position=2;
	KEY_FORWARD=ord("F");
	KEY_BACKWARD=ord("V");
	KEY_LEFT=ord("C");
	KEY_RIGHT=ord("B");
	image_blend = make_color_rgb(29, 173, 82);
	phy_rotation = 10;
}

blue1 = instance_create_layer(940, 306, "car", obj_car);
with (blue1) {
	car_position=3;
	KEY_FORWARD=ord("I");
	KEY_BACKWARD=ord("K");
	KEY_LEFT=ord("J");
	KEY_RIGHT=ord("L");
	image_blend = make_color_rgb(66, 134, 244);
	phy_rotation = 190;
}

blue2 = instance_create_layer(940, 446, "car", obj_car);
with (blue2) {
	car_position=4;
	KEY_FORWARD=vk_up;
	KEY_BACKWARD=vk_down;
	KEY_LEFT=vk_left;
	KEY_RIGHT=vk_right;
	image_blend = make_color_rgb(46, 108, 209);
	phy_rotation = 170;
}