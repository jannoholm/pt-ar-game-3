var gameid=argument[0];
var userid=argument[1];
var team=argument[2];
var position_in_team=argument[3];

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3012, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, gameid); // gameid
buffer_write(buffer, buffer_s32, userid); // userid
buffer_write(buffer, buffer_s8, team); // length
buffer_write(buffer, buffer_s8, position_in_team); // position

// send message
scr_send_packet(buffer);