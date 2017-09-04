
// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// calculate size
var bullets=ds_list_size(obj_playerinit_physics.red1.bullets);
bullets=bullets+ds_list_size(obj_playerinit_physics.red2.bullets);
bullets=bullets+ds_list_size(obj_playerinit_physics.blue1.bullets);
bullets=bullets+ds_list_size(obj_playerinit_physics.blue2.bullets);


// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3002, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, obj_server_client.gameid);
buffer_write(buffer, buffer_s32, (1*1 + 8*4) + 4 * (1*1 + 8*4 + 4*1 + 3*2) + (bullets*(1+3*4)) ); // length of update
show_debug_message("size before: " + string(buffer_tell(buffer)));

// ball position
with (obj_ball) {
	buffer_write(buffer, buffer_s8, 100);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_speed_x);
	buffer_write(buffer, buffer_f32, phy_speed_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
}

// red1 data
with (obj_playerinit_physics.red1) {
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_speed_x);
	buffer_write(buffer, buffer_f32, phy_speed_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_s8, go_move*127);
	buffer_write(buffer, buffer_s8, go_turn*127);
	buffer_write(buffer, buffer_s8, colliding);
	buffer_write(buffer, buffer_s16, damaged);
	buffer_write(buffer, buffer_s16, shoot_delay);
	buffer_write(buffer, buffer_s8, boost);
	buffer_write(buffer, buffer_s16, boost_power);
}

// red2 data
with (obj_playerinit_physics.red2) {
	buffer_write(buffer, buffer_s8, 2);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_speed_x);
	buffer_write(buffer, buffer_f32, phy_speed_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_s8, go_move*127);
	buffer_write(buffer, buffer_s8, go_turn*127);
	buffer_write(buffer, buffer_s8, colliding);
	buffer_write(buffer, buffer_s16, damaged);
	buffer_write(buffer, buffer_s16, shoot_delay);
	buffer_write(buffer, buffer_s8, boost);
	buffer_write(buffer, buffer_s16, boost_power);
}

// blue1 data
with (obj_playerinit_physics.blue1) {
	buffer_write(buffer, buffer_s8, 3);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_speed_x);
	buffer_write(buffer, buffer_f32, phy_speed_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_s8, go_move*127);
	buffer_write(buffer, buffer_s8, go_turn*127);
	buffer_write(buffer, buffer_s8, colliding);
	buffer_write(buffer, buffer_s16, damaged);
	buffer_write(buffer, buffer_s16, shoot_delay);
	buffer_write(buffer, buffer_s8, boost);
	buffer_write(buffer, buffer_s16, boost_power);
}

// blue1 data
with (obj_playerinit_physics.blue2) {
	buffer_write(buffer, buffer_s8, 4);
	buffer_write(buffer, buffer_f32, phy_angular_velocity);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_x);
	buffer_write(buffer, buffer_f32, phy_linear_velocity_y);
	buffer_write(buffer, buffer_f32, phy_speed_x);
	buffer_write(buffer, buffer_f32, phy_speed_y);
	buffer_write(buffer, buffer_f32, phy_position_x);
	buffer_write(buffer, buffer_f32, phy_position_y);
	buffer_write(buffer, buffer_f32, phy_rotation);
	buffer_write(buffer, buffer_s8, go_move*127);
	buffer_write(buffer, buffer_s8, go_turn*127);
	buffer_write(buffer, buffer_s8, colliding);
	buffer_write(buffer, buffer_s16, damaged);
	buffer_write(buffer, buffer_s16, shoot_delay);
	buffer_write(buffer, buffer_s8, boost);
	buffer_write(buffer, buffer_s16, boost_power);
}

// bullets
if (ds_list_size(obj_playerinit_physics.red1.bullets) > 0) {
	var i;
	for (i=0; i < ds_list_size(obj_playerinit_physics.red1.bullets); ++i) {
		var bullet = ds_list_find_value(obj_playerinit_physics.red1.bullets, i);
		buffer_write(buffer, buffer_s8, 101);
		with (bullet) {
			buffer_write(buffer, buffer_s32, x);
			buffer_write(buffer, buffer_s32, y);
			buffer_write(buffer, buffer_s32, phy_rotation);
		}
		show_debug_message("Bullet to update");
	}
	ds_list_clear(obj_playerinit_physics.red1.bullets);
}
if (ds_list_size(obj_playerinit_physics.red2.bullets) > 0) {
	var i;
	for (i=0; i < ds_list_size(obj_playerinit_physics.red2.bullets); ++i) {
		var bullet = ds_list_find_value(obj_playerinit_physics.red2.bullets, i);
		buffer_write(buffer, buffer_s8, 101);
		with (bullet) {
			buffer_write(buffer, buffer_s32, x);
			buffer_write(buffer, buffer_s32, y);
			buffer_write(buffer, buffer_s32, phy_rotation);
		}
		show_debug_message("Bullet to update");
	}
	ds_list_clear(obj_playerinit_physics.red2.bullets);
}
if (ds_list_size(obj_playerinit_physics.blue1.bullets) > 0) {
	var i;
	for (i=0; i < ds_list_size(obj_playerinit_physics.blue1.bullets); ++i) {
		var bullet = ds_list_find_value(obj_playerinit_physics.blue1.bullets, i);
		buffer_write(buffer, buffer_s8, 101);
		with (bullet) {
			buffer_write(buffer, buffer_s32, x);
			buffer_write(buffer, buffer_s32, y);
			buffer_write(buffer, buffer_s32, phy_rotation);
		}
		show_debug_message("Bullet to update");
	}
	ds_list_clear(obj_playerinit_physics.blue1.bullets);
}
if (ds_list_size(obj_playerinit_physics.blue2.bullets) > 0) {
	var i;
	for (i=0; i < ds_list_size(obj_playerinit_physics.blue2.bullets); ++i) {
		var bullet = ds_list_find_value(obj_playerinit_physics.blue2.bullets, i);
		buffer_write(buffer, buffer_s8, 101);
		with (bullet) {
			buffer_write(buffer, buffer_s32, x);
			buffer_write(buffer, buffer_s32, y);
			buffer_write(buffer, buffer_s32, phy_rotation);
		}
		show_debug_message("Bullet to update");
	}
	ds_list_clear(obj_playerinit_physics.blue2.bullets);
}

// send message
scr_send_packet(buffer);
