// Game state tracking object
instance_create_layer(room_width/2, room_height/2, "car", obj_gameplay);

// Game pad controls for table mode
instance_create_layer(0, 0, "car", obj_gamepad_control);

// setup cars on the grid
red1 = instance_create_layer(100, 250, "car", obj_car_with_physics);
with (red1) {
	image_blend = make_color_rgb(240,128,128);
	phy_rotation = 0;
	initialRotation = 0;
	dodraw=1;
	carId = "red1";
	teamColor = TeamColor.RED;
}

red2 = instance_create_layer(100, 570, "car", obj_car_with_physics);
// red2 = instance_create_layer(100, 570, "car", obj_car_ai_chaser);
with (red2) {
	image_blend = make_color_rgb(205,92,92);
	phy_rotation = 0;
	initialRotation = 0;
	carId = "red2";
	teamColor = TeamColor.RED;
}

blue1 = instance_create_layer(1340, 250, "car", obj_car_with_physics);
with (blue1) {
	image_blend = make_color_rgb(66, 134, 244);
	phy_rotation = 180;
	initialRotation = 180;
	carId = "blue1";
	teamColor = TeamColor.BLUE;
}

blue2 = instance_create_layer(1340, 570, "car", obj_car_with_physics);
// blue2 = instance_create_layer(1340, 570, "car", obj_car_ai_chaser);
with (blue2) {
	image_blend = make_color_rgb(46, 108, 209);
	phy_rotation = 180;
	initialRotation = 180;
	carId = "blue2";
	teamColor = TeamColor.BLUE;
}

client_map=ds_map_create();