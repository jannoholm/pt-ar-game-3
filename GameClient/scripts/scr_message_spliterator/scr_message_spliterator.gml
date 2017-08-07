/// since we are using raw type, process stream of bytes according to message rules
var buffer = argument[0];


var buffer_pos_message=0;
var buffer_pos = 0;

while (buffer_pos_message < buffer_get_size(buffer)) {
	show_debug_message("loop start, buffer_pos_message: " + string(buffer_pos_message) + ", in_buffer_size=" + string(obj_server_client.in_buffer_size) + ", buffer="+string(buffer_get_size(obj_server_client.in_buffer)));

	var packet_size = 0;

	// read packet_size, if we have enough data
	if (obj_server_client.in_buffer_size > 0) {
		if (obj_server_client.in_buffer_size < 4) {
			var copy_amount=min(buffer_get_size(buffer)-buffer_pos_message, 4-obj_server_client.in_buffer_size);
			buffer_copy(buffer, buffer_pos_message, copy_amount, obj_server_client.in_buffer, obj_server_client.in_buffer_size);
			obj_server_client.in_buffer_size += copy_amount;
			buffer_pos = copy_amount;
			show_debug_message("copied to in_buffer: " + string(copy_amount));
		}
		if (obj_server_client.in_buffer_size >= 4) {
			buffer_seek(obj_server_client.in_buffer, buffer_seek_start, 0);
			packet_size = buffer_read(obj_server_client.in_buffer, buffer_s32);
			show_debug_message("in_buffer input packet size: " + string(packet_size));
		} else {
			// data in in_buffer and nothing else to do
			return; 
		}
	} else {
		if (buffer_get_size(buffer)-buffer_pos_message < 4) {
			// not enough data to read packetsize
			var copy_amount=buffer_get_size(buffer)-buffer_pos_message;
			buffer_copy(buffer, buffer_pos_message, copy_amount, obj_server_client.in_buffer, obj_server_client.in_buffer_size);
			obj_server_client.in_buffer_size += copy_amount;
			show_debug_message("copied to in_buffer: " + string(copy_amount));
			return; 
		} else {
			buffer_seek(buffer, buffer_seek_start, buffer_pos_message);
			packet_size = buffer_read(buffer, buffer_s32);
			show_debug_message("regular packet size: " + string(packet_size));
		}
	}

	// process message, if we have enough data
	if (packet_size+4 <= obj_server_client.in_buffer_size+buffer_get_size(buffer)-buffer_pos) {
		if (obj_server_client.in_buffer_size > 0) {
			// some data in_buffer, get it all there, to pass it around
			var copy_amount=packet_size+4-obj_server_client.in_buffer_size;
			buffer_copy(buffer, buffer_pos, copy_amount, obj_server_client.in_buffer, obj_server_client.in_buffer_size);
			obj_server_client.in_buffer_size += copy_amount;
			buffer_pos += copy_amount;
			show_debug_message("copy to in_buffer: " + string(copy_amount));
		} else {
			// all data in buffer
		}
	} else {
		// not enough data. copy to in_buffer
		var copy_amount=buffer_get_size(buffer)-buffer_pos;
		buffer_copy(buffer, buffer_pos, copy_amount, obj_server_client.in_buffer, obj_server_client.in_buffer_size);
		obj_server_client.in_buffer_size += copy_amount;
		show_debug_message("copy to in_buffer: " + string(copy_amount));
		return;
	}

	show_debug_message("Passing message for processing. Size: " + string(packet_size));

	// process one message reset buffers
	if (obj_server_client.in_buffer_size > 0) {
		buffer_seek(obj_server_client.in_buffer, buffer_seek_start, 0);
		scr_receive_packet(obj_server_client.in_buffer);
		buffer_seek(obj_server_client.in_buffer, buffer_seek_start, 0);
		obj_server_client.in_buffer_size=0;
		buffer_pos_message=buffer_pos;
	} else {
		buffer_seek(buffer, buffer_seek_start, buffer_pos_message);
		scr_receive_packet(buffer);
		buffer_pos = buffer_pos_message+packet_size+4;
		buffer_pos_message = buffer_pos;
		buffer_seek(buffer, buffer_seek_start, buffer_pos);
	}
}

