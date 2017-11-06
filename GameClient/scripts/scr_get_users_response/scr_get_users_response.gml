var buffer = argument[0];

var error_code=buffer_read(buffer, buffer_s32);
var error_message=buffer_read(buffer, buffer_string);
if (error_code != 0) {
	show_debug_message("error from server: " + string(error_code) + ":" + error_message);
	return;
}

// clear existing list
var size = ds_list_size(obj_server_client.user_name_list);
for (i=0; i < size; ++i) {
	var user_obj = ds_list_find_value(obj_server_client.user_name_list, i);
	instance_destroy(user_obj);
}
ds_list_clear(obj_server_client.user_name_list);

// populate new data
var user_count=buffer_read(buffer, buffer_s32); 
show_debug_message("got some users: " + string(user_count));
var user_iterator=0;
while (user_iterator++ < user_count) {

	// read user data
	var user_bytes=buffer_read(buffer, buffer_s32); 
	var user_id=buffer_read(buffer, buffer_s32); 
	var user_name=string_lower(buffer_read(buffer, buffer_string)); 
	
	// create internal object
	var user_obj=instance_create(-1, -1, obj_user);
	user_obj.user_id = user_id;
	user_obj.user_name = user_name;
	
	// add to list, but keep list sorted
	var size = ds_list_size(obj_server_client.user_name_list);
	for (var i=0; i < size; ++i) {
		var search_obj = ds_list_find_value(obj_server_client.user_name_list, i);
		if (user_obj.user_name < search_obj.user_name) {
			ds_list_insert(obj_server_client.user_name_list, i, user_obj);
			break;
		}
	}
	
	// check if object added. if not add to end
	if ( size == ds_list_size(obj_server_client.user_name_list) ) {
		ds_list_add(obj_server_client.user_name_list, user_obj);
	}
}

// Add hardcoded AI bots, should be done via some other setting in future
var aiChaser = instance_create(-1, -1, obj_user);
// TODO: FIX ID
aiChaser.user_id = 10000;
aiChaser.user_name = string_lower("AI_BOT_CHASER");
ds_list_insert(obj_server_client.user_name_list, 0, aiChaser);
// Add hardcoded AI bots, should be done via some other setting in future
var aiDefender = instance_create(-1, -1, obj_user);
// TODO: FIX ID
aiDefender.user_id = 10001;
aiDefender.user_name = string_lower("AI_BOT_DEFENDER");
ds_list_insert(obj_server_client.user_name_list, 1, aiDefender);

size = ds_list_size(obj_server_client.user_name_list);
show_debug_message("list size: " + string(size));
for (var i=0; i < size; ++i) {
	var search_obj = ds_list_find_value(obj_server_client.user_name_list, i);
	show_debug_message("user in list: " + search_obj.user_name);
}