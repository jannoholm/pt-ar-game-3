var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string);
var controller_client=buffer_read(buffer, buffer_string);
var control_message_size=buffer_read(buffer, buffer_s32);

var go_forward=buffer_read(buffer, buffer_s8);
var go_backward=buffer_read(buffer, buffer_s8);
var go_right=buffer_read(buffer, buffer_s8);
var go_left=buffer_read(buffer, buffer_s8);

var car=ds_map_find_value(obj_playerinit_physics.client_map, controller_client);
if (!is_undefined(car)) {
	car.go_forward = go_forward;
	car.go_backward = go_backward;
	car.go_right = go_right;
	car.go_left = go_left;
}