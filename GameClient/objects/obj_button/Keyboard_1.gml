if (selected==true) {
	switch (keyboard_lastkey) {
		case vk_enter:
		case vk_space:
			scr_join_server(obj_textbox_name.txt, obj_textbox_email.txt);
			keyboard_lastkey=-1;
			break;
		case vk_tab:
			selected=false;
			obj_textbox_name.selected=true;
			keyboard_string=obj_textbox_name.txt;
			keyboard_lastkey=-1;
			break;
	}
}