obj_playerinit_physics.red1.remote_control=false;
obj_playerinit_physics.red2.remote_control=false;
obj_playerinit_physics.blue1.remote_control=false;
obj_playerinit_physics.blue2.remote_control=false;

if (obj_server_client.client_type == 0) {
	return;
}
			
var car_control = instance_create_layer(0, 0, "car", obj_hostcar_control);
car_control.car = obj_playerinit_physics.red1;

// setup drivers car
var space_pos = string_pos(" ", obj_server_client.client_name);
if (space_pos != 0) {
	car_control.car.client_name = string_copy(obj_server_client.client_name, 1, space_pos);
} else {
	car_control.car.client_name = obj_server_client.client_name;
}