
// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3002, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, obj_server_client.gameid);
buffer_write(buffer, buffer_s32, 5 * ( 1*1 + 6*4 + 4*1) ); // length of update

// ball position
with (obj_ball) {
	buffer_write(buffer, buffer_s8, 100);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_bool, false);
	buffer_write(buffer, buffer_bool, false);
	buffer_write(buffer, buffer_bool, false);
	buffer_write(buffer, buffer_bool, false);
}

// red1 data
with (obj_playerinit_physics.red1) {
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_bool, go_forward);
	buffer_write(buffer, buffer_bool, go_backward);
	buffer_write(buffer, buffer_bool, go_left);
	buffer_write(buffer, buffer_bool, go_right);
}

// red2 data
with (obj_playerinit_physics.red2) {
	buffer_write(buffer, buffer_s8, 2);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_bool, go_forward);
	buffer_write(buffer, buffer_bool, go_backward);
	buffer_write(buffer, buffer_bool, go_left);
	buffer_write(buffer, buffer_bool, go_right);
}

// blue1 data
with (obj_playerinit_physics.blue1) {
	buffer_write(buffer, buffer_s8, 3);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_bool, go_forward);
	buffer_write(buffer, buffer_bool, go_backward);
	buffer_write(buffer, buffer_bool, go_left);
	buffer_write(buffer, buffer_bool, go_right);
}


// blue1 data
with (obj_playerinit_physics.blue2) {
	buffer_write(buffer, buffer_s8, 4);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_bool, go_forward);
	buffer_write(buffer, buffer_bool, go_backward);
	buffer_write(buffer, buffer_bool, go_left);
	buffer_write(buffer, buffer_bool, go_right);
}

// send message
scr_send_packet(buffer);
