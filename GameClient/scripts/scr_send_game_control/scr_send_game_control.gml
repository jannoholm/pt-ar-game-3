var gameid=argument[0];
var client_id=argument[1];
var go_move=argument[2];
var go_turn=argument[3];
var boost=argument[4];
var shoot=argument[5];
var highlight=argument[6];

// reset buffer
var buffer=obj_server_client.out_buffer;
buffer_seek(buffer, buffer_seek_start, 0);
buffer_seek(buffer, buffer_seek_end, 0);

// write content
obj_server_client.messageid_counter+=10000;
scr_write_messageheader(buffer, 3000, obj_server_client.messageid_counter, obj_server_client.client_id);
buffer_write(buffer, buffer_string, gameid); // gameid
buffer_write(buffer, buffer_string, client_id); // client_id
buffer_write(buffer, buffer_s32, 5); // length
buffer_write(buffer, buffer_s8, go_move*127); // go_move
buffer_write(buffer, buffer_s8, go_turn*127); // go_turn
buffer_write(buffer, buffer_s8, boost); // boost
buffer_write(buffer, buffer_s8, shoot); // shoot
buffer_write(buffer, buffer_s8, highlight); // highlight

// send message
scr_send_packet(buffer);
