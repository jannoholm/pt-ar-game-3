/// @description initialize connection to proxy

var conf=instance_create_layer(0,0,"Instances", obj_configuration);

var type = network_socket_tcp;
var ip = conf.ip_address
var port = conf.port;
//network_set_config(network_config_use_non_blocking_socket, 1);
network_set_config(network_config_connect_timeout, 5000);
socket = network_create_socket(type);
connection = network_connect_raw(socket, ip, port);

var size = 16384;
var allignment = 1;
out_buffer = buffer_create(size, buffer_fixed, allignment);

messageid_counter=10000+random(10000);
in_buffer = buffer_create(size, buffer_fixed, allignment);
in_buffer_size = 0;

// read if game is table
client_type=conf.client_type;

connected=false;
joined=false;
client_id="";
client_name="not set";
client_email="not set";
gameid="";

show_debug_message("create server_client");

if (room==play) {
	if (somenonsens=1) return;
}
room_goto_next();