// Game state tracking object
instance_create_layer(room_width/2, room_height/2, "goal", obj_gameplay);
instance_create_layer(room_width/2, room_height/2, "gameplay", obj_timer);
obj_timer.g=obj_gameplay;

// Game pad controls for table mode
instance_create_layer(0, 0, "car", obj_gamepad_control);

// setup cars on the grid
object_set_sprite( obj_car_with_physics, spr_mycar1 );
red1 = instance_create_layer(200, 350, "car", obj_car_with_physics);
with (red1) {
	initialPosX=100;
	//image_blend = make_color_rgb(240,128,128);
	phy_rotation = 0;
	initialRotation = 0;
	dodraw=1;
	carId = "red1";
	teamColor = TeamColor.RED;
	playerType = PlayerType.PLAYER;
	keyboard_enabler=ord("1");
}

object_set_sprite( obj_car_with_physics, spr_mycar2 );
red2 = instance_create_layer(200, 730, "car", obj_car_with_physics);
with (red2) {
	initialPosX=100;
	//image_blend = make_color_rgb(205,92,92);
	phy_rotation = 0;
	initialRotation = 0;
	carId = "red2";
	teamColor = TeamColor.RED;
	playerType = PlayerType.PLAYER;
	keyboard_enabler=ord("3");
}


object_set_sprite( obj_car_with_physics, spr_mycar3 );
blue1 = instance_create_layer(1720, 350, "car", obj_car_with_physics);
with (blue1) {
	initialPosX=1820;
	//image_blend = make_color_rgb(66, 134, 244);
	phy_rotation = 180;
	initialRotation = 180;
	carId = "blue1";
	teamColor = TeamColor.BLUE;
	playerType = PlayerType.PLAYER;
	keyboard_enabler=ord("2");
}

object_set_sprite( obj_car_with_physics, spr_mycar4 );
blue2 = instance_create_layer(1720, 730, "car", obj_car_with_physics);
with (blue2) {
	initialPosX=1820;
	//image_blend = make_color_rgb(46, 108, 209);
	phy_rotation = 180;
	initialRotation = 180;
	carId = "blue2";
	teamColor = TeamColor.BLUE;
	playerType = PlayerType.PLAYER;
	keyboard_enabler=ord("4");
}

client_map=ds_map_create();