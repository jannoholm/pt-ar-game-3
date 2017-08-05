var socket_id=argument0;
var buffer=argument1;
var client_id_current=clientmap[? string(socket_id)].client_id;

while (true) {
	var message_type=buffer_read(buffer, buffer_u8);
	switch(message_type) {
		case MESSAGE_MOVE:
			var xx = buffer_read(buffer, buffer_f16);
			var yy = buffer_read(buffer, buffer_f16);
			buffer_seek(send_buffer, buffer_seek_start, 0);
			buffer_write(send_buffer, buffer_u8, MESSAGE_MOVE_NOTIFICATION);
			buffer_write(send_buffer, buffer_u16, client_id_current);
			buffer_write(send_buffer, buffer_u16, xx);
			buffer_write(send_buffer, buffer_u16, yy);
			with (obj_server_client) {
				if (self.client_id != client_id_current) {
					network_send_raw(self.socket_id, other.send_buffer, 7);
				}
			}
			break;
	}
	
	if (buffer_tell(buffer) == buffer_get_size(buffer)) {
		break;
	}
}