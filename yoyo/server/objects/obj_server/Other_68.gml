var type_event = async_load[? "type"];

switch (type_event) {
	case network_type_connect:
		ds_list_add( sockets, async_load[? "socket"] );
		break;
	case network_type_disconnect:
		var pos = ds_list_find_index( sockets, async_load[? "socket"] );
		ds_list_delete( sockets, pos );
		break;
	case network_type_data:
		var buffer = async_load[? "buffer"];
		buffer_seek(buffer, buffer_seek_start, 0);
		scr_receive_packet(buffer);
		break;
}