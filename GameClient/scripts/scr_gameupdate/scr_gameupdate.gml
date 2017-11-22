var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string); 
var control_length=buffer_read(buffer, buffer_s32); 
show_debug_message("control_length="+string(control_length));

var i;
while (control_length > 0) {
	var carid=buffer_read(buffer, buffer_s8);
	control_length=control_length-1;
	show_debug_message("carid: " + string(carid) + ", remain: " + string(control_length));

	switch (carid) {
		case 100: // gameplay state
			with (obj_gameplay){
				currentGamePhase = buffer_read(buffer, buffer_s8);
				currentCarPhase = buffer_read(buffer, buffer_s8);
				teamRedScore = buffer_read(buffer, buffer_s8);
				teamBlueScore= buffer_read(buffer, buffer_s8);
				buffer_read(buffer, buffer_s32); // tick could be used to sync
				show_debug_message("Game state: " + string(currentGamePhase) + "-" + string(currentCarPhase) + "-" + string(teamRedScore) + "-" + string(teamBlueScore) );
			}
			control_length=control_length-1*4;
			break;
		case 101: // ball
		
			var ballExists = buffer_read(buffer, buffer_s8);
			var angular_velocity = buffer_read(buffer, buffer_f32);
			var linear_velocity_x = buffer_read(buffer, buffer_f32);
			var linear_velocity_y = buffer_read(buffer, buffer_f32);
			var speed_x = buffer_read(buffer, buffer_f32);
			var speed_y = buffer_read(buffer, buffer_f32);
			var position_x = buffer_read(buffer, buffer_f32);
			var position_y = buffer_read(buffer, buffer_f32);
			var rotation = buffer_read(buffer, buffer_f32);
			control_length=control_length-1-8*4;
			
			show_debug_message("Ball exists: " + string(ballExists) + " " + string( ballExists ? "true" : "false" ))
			
			if ( ballExists > 0 ) {
				with ( obj_ball ){
					phy_angular_velocity = angular_velocity;
					phy_linear_velocity_x = linear_velocity_x;
					phy_linear_velocity_y = linear_velocity_y;
					phy_speed_x = speed_x;
					phy_speed_y = speed_y;
					phy_position_x = position_x;
					phy_position_y = position_y;
					phy_rotation = rotation;
				}
			} else {
				instance_destroy(obj_ball);
			}

			break;
		case 110: // bullet
			var position_x = buffer_read(buffer, buffer_f32);
			var position_y = buffer_read(buffer, buffer_f32);
			var rotation = buffer_read(buffer, buffer_f32);
			var bullet=instance_create_layer(position_x,position_y,"car", obj_bullet);
			bullet.phy_rotation = rotation;
			with(bullet){
				physics_apply_local_force(0, 0, phy_mass*1000, 0);
			}
			control_length=control_length-3*4;
show_debug_message("Bullet created at " + string(position_x) + ":" + string(position_y));
			break;
		case 1:
			with (obj_playerinit_physics.red1) {
				phy_angular_velocity = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_x = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_y = buffer_read(buffer, buffer_f32);
				phy_speed_x = buffer_read(buffer, buffer_f32);
				phy_speed_y = buffer_read(buffer, buffer_f32);
				phy_position_x = buffer_read(buffer, buffer_f32);
				phy_position_y = buffer_read(buffer, buffer_f32);
				phy_rotation = buffer_read(buffer, buffer_f32);
				var read_go_move = buffer_read(buffer, buffer_s8)/127.0;
				var read_go_turn = buffer_read(buffer, buffer_s8)/127.0;
				var read_colliding = buffer_read(buffer, buffer_s8);
				var read_damaged = buffer_read(buffer, buffer_s16);
				var read_shoot_delay = buffer_read(buffer, buffer_s16);
				var read_boost = buffer_read(buffer, buffer_s8);
				var read_boost_power = buffer_read(buffer, buffer_s16);
				if (remote_control) {
					go_move = read_go_move;
					go_turn = read_go_turn;
					colliding = read_colliding;
					shoot_delay = read_shoot_delay;
					boost = read_boost;
					boost_power = read_boost_power;
				}
				damaged = read_damaged;
			}
			control_length=control_length-8*4-4*1-3*2;
			break;
		case 2:
			with (obj_playerinit_physics.red2){
				phy_angular_velocity = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_x = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_y = buffer_read(buffer, buffer_f32);
				phy_speed_x = buffer_read(buffer, buffer_f32);
				phy_speed_y = buffer_read(buffer, buffer_f32);
				phy_position_x = buffer_read(buffer, buffer_f32);
				phy_position_y = buffer_read(buffer, buffer_f32);
				phy_rotation = buffer_read(buffer, buffer_f32);
				var read_go_move = buffer_read(buffer, buffer_s8)/127.0;
				var read_go_turn = buffer_read(buffer, buffer_s8)/127.0;
				var read_colliding = buffer_read(buffer, buffer_s8);
				var read_damaged = buffer_read(buffer, buffer_s16);
				var read_shoot_delay = buffer_read(buffer, buffer_s16);
				var read_boost = buffer_read(buffer, buffer_s8);
				var read_boost_power = buffer_read(buffer, buffer_s16);
				if (remote_control) {
					go_move = read_go_move;
					go_turn = read_go_turn;
					colliding = read_colliding;
					shoot_delay = read_shoot_delay;
					boost = read_boost;
					boost_power = read_boost_power;
				}
				damaged = read_damaged;
			}
			control_length=control_length-8*4-4*1-3*2;
			break;
		case 3:
			with (obj_playerinit_physics.blue1){
				phy_angular_velocity = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_x = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_y = buffer_read(buffer, buffer_f32);
				phy_speed_x = buffer_read(buffer, buffer_f32);
				phy_speed_y = buffer_read(buffer, buffer_f32);
				phy_position_x = buffer_read(buffer, buffer_f32);
				phy_position_y = buffer_read(buffer, buffer_f32);
				phy_rotation = buffer_read(buffer, buffer_f32);
				var read_go_move = buffer_read(buffer, buffer_s8)/127.0;
				var read_go_turn = buffer_read(buffer, buffer_s8)/127.0;
				var read_colliding = buffer_read(buffer, buffer_s8);
				var read_damaged = buffer_read(buffer, buffer_s16);
				var read_shoot_delay = buffer_read(buffer, buffer_s16);
				var read_boost = buffer_read(buffer, buffer_s8);
				var read_boost_power = buffer_read(buffer, buffer_s16);
				if (remote_control) {
					go_move = read_go_move;
					go_turn = read_go_turn;
					colliding = read_colliding;
					shoot_delay = read_shoot_delay;
					boost = read_boost;
					boost_power = read_boost_power;
				}
				damaged = read_damaged;
			}
			control_length=control_length-8*4-4*1-3*2;
			break;
		case 4:
			with (obj_playerinit_physics.blue2){
				phy_angular_velocity = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_x = buffer_read(buffer, buffer_f32);
				phy_linear_velocity_y = buffer_read(buffer, buffer_f32);
				phy_speed_x = buffer_read(buffer, buffer_f32);
				phy_speed_y = buffer_read(buffer, buffer_f32);
				phy_position_x = buffer_read(buffer, buffer_f32);
				phy_position_y = buffer_read(buffer, buffer_f32);
				phy_rotation = buffer_read(buffer, buffer_f32);
				var read_go_move = buffer_read(buffer, buffer_s8)/127.0;
				var read_go_turn = buffer_read(buffer, buffer_s8)/127.0;
				var read_colliding = buffer_read(buffer, buffer_s8);
				var read_damaged = buffer_read(buffer, buffer_s16);
				var read_shoot_delay = buffer_read(buffer, buffer_s16);
				var read_boost = buffer_read(buffer, buffer_s8);
				var read_boost_power = buffer_read(buffer, buffer_s16);
				if (remote_control) {
					go_move = read_go_move;
					go_turn = read_go_turn;
					colliding = read_colliding;
					shoot_delay = read_shoot_delay;
					boost = read_boost;
					boost_power = read_boost_power;
				}
				damaged = read_damaged;
			}
			control_length=control_length-8*4-4*1-3*2;
			break;
		default:
			show_debug_message("Unknown carid: " + string(carid));
			return;
	}
}
