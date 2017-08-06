max_width = 600;
selected = true;
blink = false;
txt = "";
alarm[0] = 3;
can_click=true;

// store to disk first
var filename=working_directory + "/user_details.txt";
if (file_exists(filename)) {
	var filehandle = file_text_open_read(filename);
	txt=file_text_read_string(filehandle);file_text_readln(filehandle);
	file_text_close(filehandle);
}