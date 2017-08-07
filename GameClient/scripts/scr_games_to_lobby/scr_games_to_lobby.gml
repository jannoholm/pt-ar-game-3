var buffer=argument[0];

var error_code=buffer_read(buffer, buffer_s32);
var error_message=buffer_read(buffer, buffer_string);
if (error_code != 0) {
	show_debug_message("error from server: " + string(error_code) + ":" + error_message);
	return;
}

var i;
// clear existing list
var size = ds_list_size(obj_game_search.games);
for (i=0; i < size; ++i) {
	var game = ds_list_find_value(obj_game_search.games, i);
	instance_destroy(game);
}
ds_list_clear(obj_game_search.games);

// new elements to list
var number_of_games=buffer_read(buffer, buffer_s32);
show_debug_message("getgames.response: number of games=" + string(number_of_games));
for (i=0; i < number_of_games; ++i) {
	var byte_count=buffer_read(buffer, buffer_s32);
	var game=instance_create_layer(50, 150 + 50*i, "gamelist", obj_lobby_game);
	game.gameid = buffer_read(buffer, buffer_string);
	game.name = string_upper(buffer_read(buffer, buffer_string));
	game.total_players = buffer_read(buffer, buffer_s32);
	game.free_positions = buffer_read(buffer, buffer_s32);
	game.ai_type = buffer_read(buffer, buffer_string);
	ds_list_add(obj_game_search.games, game);
}

