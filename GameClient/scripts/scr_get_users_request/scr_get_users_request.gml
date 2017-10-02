var filter_str="";

show_debug_message("getting users: " + obj_server_client.client_id + ", " + filter_str);

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3010, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, filter_str);

// send message
scr_send_packet(buffer);
