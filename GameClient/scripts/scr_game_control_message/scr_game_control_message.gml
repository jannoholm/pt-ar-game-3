var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string);
var controller_client=buffer_read(buffer, buffer_string);
var control_message_size=buffer_read(buffer, buffer_s32);

var go_move=buffer_read(buffer, buffer_s8);
var go_turn=buffer_read(buffer, buffer_s8);
var boost=buffer_read(buffer, buffer_s8);
var shoot=buffer_read(buffer, buffer_s8);
var highlight=buffer_read(buffer, buffer_s8);

var car=ds_map_find_value(obj_playerinit_physics.client_map, controller_client);
if (!is_undefined(car)) {
	car.go_move = go_move/127.0;
	car.go_turn = go_turn/127.0;
	car.boost = boost;
	car.shoot = shoot;
	car.highlight = highlight;
}