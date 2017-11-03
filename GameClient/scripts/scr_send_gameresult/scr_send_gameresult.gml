// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write header
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3014, obj_server_client.messageid_counter, obj_server_client.client_id);

// write content
if (obj_gameplay.teamRedScore > obj_gameplay.teamBlueScore) {
	buffer_write(buffer, buffer_s8, 1);
} else if (obj_gameplay.teamRedScore < obj_gameplay.teamBlueScore) {
	buffer_write(buffer, buffer_s8, 2);
} else {
	buffer_write(buffer, buffer_s8, 0);
}
// number of players
buffer_write(buffer, buffer_s32, 4);
var player_length=1+1+4*4;

// red1
with (obj_playerinit_physics.red1) {
	buffer_write(buffer, buffer_s32, player_length);
	buffer_write(buffer, buffer_s8, 0);
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_s32, score_goals);
	buffer_write(buffer, buffer_s32, score_bullet_hits);
	buffer_write(buffer, buffer_s32, score_ball_touches);
	buffer_write(buffer, buffer_s32, score_boost_touches);
	score_gamescore = 0;
}

// red2
with (obj_playerinit_physics.red2) {
	buffer_write(buffer, buffer_s32, player_length);
	buffer_write(buffer, buffer_s8, 0);
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_s32, score_goals);
	buffer_write(buffer, buffer_s32, score_bullet_hits);
	buffer_write(buffer, buffer_s32, score_ball_touches);
	buffer_write(buffer, buffer_s32, score_boost_touches);
	score_gamescore = 0;
}

// blue1
with (obj_playerinit_physics.blue1) {
	buffer_write(buffer, buffer_s32, player_length);
	buffer_write(buffer, buffer_s8, 0);
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_s32, score_goals);
	buffer_write(buffer, buffer_s32, score_bullet_hits);
	buffer_write(buffer, buffer_s32, score_ball_touches);
	buffer_write(buffer, buffer_s32, score_boost_touches);
	score_gamescore = 0;
}

// blue2
with (obj_playerinit_physics.blue2) {
	buffer_write(buffer, buffer_s32, player_length);
	buffer_write(buffer, buffer_s8, 0);
	buffer_write(buffer, buffer_s8, 1);
	buffer_write(buffer, buffer_s32, score_goals);
	buffer_write(buffer, buffer_s32, score_bullet_hits);
	buffer_write(buffer, buffer_s32, score_ball_touches);
	buffer_write(buffer, buffer_s32, score_boost_touches);
	score_gamescore = 0;
}

// send message
scr_send_packet(buffer);
show_debug_message("results sent to server.")
