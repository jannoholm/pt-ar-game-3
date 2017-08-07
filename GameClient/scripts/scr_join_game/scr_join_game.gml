var gameid=argument[0];

show_debug_message("Join game: " + gameid);

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 2006, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, gameid); // gameid
buffer_write(buffer, buffer_s8, 0); // random team

obj_server_client.gameid=gameid;

// send message
scr_send_packet(buffer);
