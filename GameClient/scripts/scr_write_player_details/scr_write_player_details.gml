// store to disk first
var filehandle = file_text_open_write(working_directory + "/user_details.txt");
file_text_write_string(filehandle, obj_server_client.client_name); file_text_writeln(filehandle);
file_text_write_string(filehandle, obj_server_client.client_email); file_text_writeln(filehandle);
file_text_write_string(filehandle, obj_server_client.client_id); file_text_writeln(filehandle);
file_text_close(filehandle);
