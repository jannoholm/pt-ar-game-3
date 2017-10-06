selected=false;

// store to disk first
var filename=working_directory + "/user_details.txt";
if (file_exists(filename)) {
	obj_textbox_name.selected=false;
	obj_textbox_email.selected=false;
	obj_login_button.selected=true;
}

if (obj_server_client.client_type == 0) {
	obj_server_client.client_name="TABLE";
	obj_server_client.client_email="info@playtech.com";

	scr_join_server(obj_server_client.client_name, obj_server_client.client_email);
}