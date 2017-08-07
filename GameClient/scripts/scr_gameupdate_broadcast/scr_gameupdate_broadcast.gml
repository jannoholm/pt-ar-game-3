
// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3002, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, obj_server_client.gameid);
buffer_write(buffer, buffer_s32, 65); // length of update

// ball position
buffer_write(buffer, buffer_s8, 100);
buffer_write(buffer, buffer_f32, obj_ball.x);
buffer_write(buffer, buffer_f32, obj_ball.y);
buffer_write(buffer, buffer_f32, obj_ball.phy_rotation);

// red1 data
buffer_write(buffer, buffer_s8, 1);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red1.x);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red1.y);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red1.phy_rotation);

// red2 data
buffer_write(buffer, buffer_s8, 2);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red2.x);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red2.y);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.red2.phy_rotation);

// blue1 data
buffer_write(buffer, buffer_s8, 3);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue1.x);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue1.y);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue1.phy_rotation);

// blue2 data
buffer_write(buffer, buffer_s8, 4);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue2.x);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue2.y);
buffer_write(buffer, buffer_f32, obj_playerinit_physics.blue2.phy_rotation);

// send message
scr_send_packet(buffer);
