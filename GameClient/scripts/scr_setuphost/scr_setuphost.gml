obj_playerinit_physics.red1.remote_control=false;
obj_playerinit_physics.red2.remote_control=false;
obj_playerinit_physics.blue1.remote_control=false;
obj_playerinit_physics.blue2.remote_control=false;

// Table mode, all cars controlled by gamepad
if ( obj_server_client.client_type == 0 ) {
	show_debug_message("Creating gamepad controls for table cars")
	var gamepadControl = instance_create_layer(0, 0, "car", obj_tablecar_control);
	with ( gamepadControl ) {
		car = obj_playerinit_physics.red1;
	}
	
	var gamepadControl = instance_create_layer(0, 0, "car", obj_tablecar_control);
	with ( gamepadControl ) {
		car = obj_playerinit_physics.red2;
	}
	var gamepadControl = instance_create_layer(0, 0, "car", obj_tablecar_control);
	with ( gamepadControl ) {
		car = obj_playerinit_physics.blue1;
	}
	var gamepadControl = instance_create_layer(0, 0, "car", obj_tablecar_control);
	with ( gamepadControl ) {
		car = obj_playerinit_physics.blue2;
	}
} else {
	var car_control = instance_create_layer(0, 0, "car", obj_hostcar_control);
	car_control.car = obj_playerinit_physics.red1;

	// setup drivers car
	var space_pos = string_pos(" ", obj_server_client.client_name);
	if (space_pos != 0) {
		car_control.car.client_name = string_copy(obj_server_client.client_name, 1, space_pos);
	} else {
		car_control.car.client_name = obj_server_client.client_name;
	}
}

instance_create_layer(0, 0, "car", obj_gamestate_push);
