/// @description initialize the client

var type = network_socket_tcp;
var ip = "127.0.0.1";
var port = 8000;
socket = network_create_socket(type);
connection = network_connect(socket, ip, port);

var size = 1024;
var type = buffer_fixed;
var allignment = 1;
buffer = buffer_create(size, type, allignment);
