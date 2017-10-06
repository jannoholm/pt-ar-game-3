// store to disk first
filename=working_directory + "/user_details.txt";
client_name="";
client_email="";
client_clientid="";

if (file_exists(filename)) {
	var filehandle = file_text_open_read(filename);
	client_name=file_text_read_string(filehandle);file_text_readln(filehandle);
	client_email=file_text_read_string(filehandle);file_text_readln(filehandle);
	client_id=file_text_read_string(filehandle);file_text_readln(filehandle);
	file_text_close(filehandle);
}

