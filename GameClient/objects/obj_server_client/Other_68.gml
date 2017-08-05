
var type_event = async_load[? "type"];

switch (type_event) {
	case network_type_connect:
	case network_type_non_blocking_connect:
		show_debug_message("connection with server set up.");
		connected=true;
		break;
	case network_type_disconnect:
		show_debug_message("connection with server closed.");
		connected=false;
		break;
	case network_type_data:
		var buffer = async_load[? "buffer"];
		buffer_seek(buffer, buffer_seek_start, 0);
		show_debug_message("got some data: " + string(buffer_get_size(buffer)));
		scr_message_spliterator(buffer);
		break;
}
