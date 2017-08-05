/// send data to socket
var buffer=argument[0];

scr_set_messageLength(buffer);
network_send_raw(obj_server_client.socket, buffer, buffer_tell(buffer));

show_debug_message("Sending to socket: " + string(buffer_tell(buffer)));
/*
buffer_seek(buffer, buffer_seek_start, 0);
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
scr_writehex( buffer_read(buffer, buffer_s8) );
*/