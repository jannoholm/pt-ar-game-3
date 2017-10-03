var car=argument[0];

var space_pos = string_pos(" ", car.show_user_select_name);
if (space_pos != 0) {
	car.client_name = string_copy(car.show_user_select_name, 1, space_pos);
} else {
	car.client_name = car.show_user_select_name;
}

// TODO: send to server