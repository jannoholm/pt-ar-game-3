car_control = instance_create_layer(0, 0, "car", obj_join_car_control);

// setup cars on the grid
green1 = instance_create_layer(100, 306, "car", obj_simple_car);
with (green1) {
	car_position=1;
	image_blend = make_color_rgb(55, 229, 119);
	image_angle = -10;
}

green2 = instance_create_layer(100, 446, "car", obj_simple_car);
with (green2) {
	car_position=2;
	image_blend = make_color_rgb(29, 173, 82);
	image_angle = 10;
}

blue1 = instance_create_layer(940, 306, "car", obj_simple_car);
with (blue1) {
	car_position=3;
	image_blend = make_color_rgb(66, 134, 244);
	image_angle = 190;
}

blue2 = instance_create_layer(940, 446, "car", obj_simple_car);
with (blue2) {
	car_position=4;
	image_blend = make_color_rgb(46, 108, 209);
	image_angle = 170;
}

client_map=ds_map_create();