var name=argument[0];
var email=argument[1];

show_debug_message("Sending join: " + name + " " + email);
obj_server_client.client_name=name;
obj_server_client.client_email=email;

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 1002, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, name);
buffer_write(buffer, buffer_string, email);

// send message
scr_send_packet(buffer);
