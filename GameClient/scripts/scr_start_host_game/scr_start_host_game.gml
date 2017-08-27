var name=argument[0];

show_debug_message("Start hosting game: " + name);

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 2008, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_s32, 4); // total players
buffer_write(buffer, buffer_string, ""); // ai type
if (obj_server_client.client_type == 0) {
	buffer_write(buffer, buffer_s8, 0); // just table
} else {
	buffer_write(buffer, buffer_s8, 1); // join as player
}

// send message
scr_send_packet(buffer);
