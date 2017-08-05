if (selected==true) {
	switch (keyboard_lastkey) {
		case vk_tab:
		case vk_enter:
			selected=false;
			obj_button.selected=true;
			keyboard_string="";
			keyboard_lastkey=-1;
			break;
		default:
			keyboard_string=string_lower(keyboard_string);
			if (string_width(keyboard_string) < max_width) {
				txt = keyboard_string;
			} else {
				keyboard_string=txt;
			}
	}
}

