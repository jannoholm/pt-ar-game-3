selected=false;

// store to disk first
var filename=working_directory + "/user_details.txt";
if (file_exists(filename)) {
	obj_textbox_name.selected=false;
	obj_textbox_email.selected=false;
	obj_login_button.selected=true;
}