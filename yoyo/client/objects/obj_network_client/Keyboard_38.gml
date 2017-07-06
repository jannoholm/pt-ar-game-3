
	buffer_seek(buffer, buffer_seek_start, 0);
	buffer_write(buffer, buffer_u16, 1);
	buffer_write(buffer, buffer_u8, 1); 
	network_send_packet(socket, buffer, buffer_tell(buffer));
