car_control = instance_create_layer(0, 0, "car", obj_hostcar_control);

// setup drivers car
red1 = instance_create_layer(100, 306, "car", obj_car_with_physics);
var space_pos = string_pos(" ", obj_server_client.client_name);
if (space_pos != 0) {
	red1.client_name = string_copy(obj_server_client.client_name, 1, space_pos);
} else {
	red1.client_name = obj_server_client.client_name;
}
obj_hostcar_control.car = red1;

// setup cars on the grid
with (red1) {
	car_position=1;
	image_blend = make_color_rgb(55, 229, 119);
	phy_rotation = -10;
}

red2 = instance_create_layer(100, 446, "car", obj_car_with_physics);
with (red2) {
	car_position=2;
	image_blend = make_color_rgb(29, 173, 82);
	phy_rotation = 10;
}

blue1 = instance_create_layer(940, 306, "car", obj_car_with_physics);
with (blue1) {
	car_position=3;
	image_blend = make_color_rgb(66, 134, 244);
	phy_rotation = 190;
}

blue2 = instance_create_layer(940, 446, "car", obj_car_with_physics);
with (blue2) {
	car_position=4;
	image_blend = make_color_rgb(46, 108, 209);
	phy_rotation = 170;
}

client_map=ds_map_create();