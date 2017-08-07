if (selected==true) {
	switch (keyboard_lastkey) {
		case vk_tab:
		case vk_enter:
			selected=false;
			obj_textbox_email.selected=true;
			obj_button.selected=false;
			keyboard_string=obj_textbox_email.txt;
			keyboard_lastkey=-1;
			break;
		default:
			keyboard_string=string_upper(keyboard_string);
			if (string_width(keyboard_string) < max_width) {
				txt = keyboard_string;
			} else {
				keyboard_string=txt;
			}
	}	
}
