var type = network_socket_tcp;
var port = 8000;
max_clients = 10;
server = network_create_server(type, port, max_clients);
sockets = ds_list_create();