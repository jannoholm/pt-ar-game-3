var buffer=argument[0];

var gameid=buffer_read(buffer, buffer_string);
var game_name=buffer_read(buffer, buffer_string);
var total_places=buffer_read(buffer, buffer_s32);
var free_places=buffer_read(buffer, buffer_s32);
var game_status=buffer_read(buffer, buffer_s8);
var aiType=buffer_read(buffer, buffer_string);
var current_players=buffer_read(buffer, buffer_s32);
show_debug_message("lobby update received. players: " + string(current_players) + ", game_status="+string(game_status) + ", aiType=" + aiType);
var obj_playerinit;
if (room==host_play) {
	obj_playerinit=obj_playerinit_physics;
} else if (room==host_join) {
	obj_playerinit=obj_playerinit_nophysics;
}
var i;
for (i=0; i < current_players; i++) {
	var player_bytes=buffer_read(buffer, buffer_s32);
	var player_client_id=buffer_read(buffer, buffer_string);
	var player_client_name=buffer_read(buffer, buffer_string);
	var player_team=buffer_read(buffer, buffer_s8);
	var player_position_in_team=buffer_read(buffer, buffer_s32);
	show_debug_message("player: " + player_client_id + ", " + player_client_name + ", " + string(player_team) + ", " + string(player_position_in_team));
	if (player_team == 0) {
		if (player_position_in_team=1) {
			obj_playerinit.green1.client_id=player_client_id;
			obj_playerinit.green1.client_name=player_client_name;
			ds_map_add(obj_playerinit.client_map, player_client_id, obj_playerinit.green1);
			show_debug_message("player to green1: " + player_client_name);
		} else if (player_position_in_team=2) {
			obj_playerinit.green2.client_id=player_client_id;
			obj_playerinit.green2.client_name=player_client_name;
			ds_map_add(obj_playerinit.client_map, player_client_id, obj_playerinit.green2);
			show_debug_message("player to green2: " + player_client_name);
		}
	} else if (player_team == 1) {
		if (player_position_in_team=1) {
			obj_playerinit.blue1.client_id=player_client_id;
			obj_playerinit.blue1.client_name=player_client_name;
			ds_map_add(obj_playerinit.client_map, player_client_id, obj_playerinit.blue1);
			show_debug_message("player to blue1: " + player_client_name);
		} else if (player_position_in_team=2) {
			obj_playerinit.blue2.client_id=player_client_id;
			obj_playerinit.blue2.client_name=player_client_name;
			ds_map_add(obj_playerinit.client_map, player_client_id, obj_playerinit.blue2);
			show_debug_message("player to blue2: " + player_client_name);
		}
	}
}