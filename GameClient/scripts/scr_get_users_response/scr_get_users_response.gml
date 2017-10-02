var buffer = argument[0];

var error_code=buffer_read(buffer, buffer_s32);
var error_message=buffer_read(buffer, buffer_string);
if (error_code != 0) {
	show_debug_message("error from server: " + string(error_code) + ":" + error_message);
	return;
}

// clear existing list
var size = ds_list_size(obj_hostcar_control.car.user_name_list);
for (i=0; i < size; ++i) {
	var user_obj = ds_list_find_value(obj_hostcar_control.car.user_name_list, i);
	instance_destroy(user_obj);
}
ds_list_clear(obj_hostcar_control.car.user_name_list);

// populate new data
var user_count=buffer_read(buffer, buffer_s32); 
show_debug_message("got some users: " + string(user_count));
var user_iterator=0;
while (user_iterator++ < user_count) {
	show_debug_message("user: " + string(i));

	// read user data
	var user_bytes=buffer_read(buffer, buffer_s32); 
	var user_id=buffer_read(buffer, buffer_s32); 
	var user_name=buffer_read(buffer, buffer_string); 
	
	// create internal object
	var user_obj=instance_create(-1, -1, obj_user);
	user_obj.user_id = user_id;
	user_obj.user_name = user_name;
	
	// add to list, but keep list sorted
	var size = ds_list_size(obj_hostcar_control.car.user_name_list);
	for (i=0; i < size; ++i) {
		var search_obj = ds_list_find_value(obj_hostcar_control.car.user_name_list, i);
		if (user_obj.user_name < search_obj.user_name) {
			ds_list_insert(obj_hostcar_control.car.user_name_list, i, user_obj);
		}
	}
	
	// check if object added. if not add to end
	if ( size == ds_list_size(obj_hostcar_control.car.user_name_list) ) {
		ds_list_add(obj_hostcar_control.car.user_name_list, user_obj);
	}
}