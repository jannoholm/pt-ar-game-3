var filter_str=argument[0];
var include_all=argument[1];

show_debug_message("getting games: " + obj_server_client.client_id + ", " + filter_str + ", " + string(include_all));

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 2000, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, filter_str);
buffer_write(buffer, buffer_s8, include_all > 0 ? 1 : 0);

// send message
scr_send_packet(buffer);
