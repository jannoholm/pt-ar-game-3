var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string); 
var control_length=buffer_read(buffer, buffer_s32); 

var i;
for (i=0; i<5; ++i) {
	var carid=buffer_read(buffer, buffer_s8);
	var angular_velocity=buffer_read(buffer, buffer_f32);
	var linear_velocity_x=buffer_read(buffer, buffer_f32);
	var linear_velocity_y=buffer_read(buffer, buffer_f32);
	var position_x=buffer_read(buffer, buffer_f32);
	var position_y=buffer_read(buffer, buffer_f32);
	var rotation=buffer_read(buffer, buffer_f32);

	var forward = buffer_read(buffer, buffer_bool);
	var backward = buffer_read(buffer, buffer_bool);
	var left = buffer_read(buffer, buffer_bool);
	var right = buffer_read(buffer, buffer_bool);

	switch (carid) {
		case 100: // ball
			with (obj_ball){
				phy_angular_velocity = angular_velocity;
				phy_linear_velocity_x = linear_velocity_x;
				phy_linear_velocity_y = linear_velocity_y;
				phy_position_x = position_x;
				phy_position_y = position_y;
				phy_rotation = rotation;
			}
			break;
		case 1:
			with (obj_playerinit_physics.red1) {
				phy_angular_velocity = angular_velocity;
				phy_linear_velocity_x = linear_velocity_x;
				phy_linear_velocity_y = linear_velocity_y;
				phy_position_x = position_x;
				phy_position_y = position_y;
				phy_rotation = rotation;
				go_forward = forward;
				go_backward = backward;
				go_left = left;
				go_right = right;
			}
			break;
		case 2:
			with (obj_playerinit_physics.red2){
				phy_angular_velocity = angular_velocity;
				phy_linear_velocity_x = linear_velocity_x;
				phy_linear_velocity_y = linear_velocity_y;
				phy_position_x = position_x;
				phy_position_y = position_y;
				phy_rotation = rotation;
				go_forward = forward;
				go_backward = backward;
				go_left = left;
				go_right = right;
			}
			break;
		case 3:
			with (obj_playerinit_physics.blue1){
				phy_angular_velocity = angular_velocity;
				phy_linear_velocity_x = linear_velocity_x;
				phy_linear_velocity_y = linear_velocity_y;
				phy_position_x = position_x;
				phy_position_y = position_y;
				phy_rotation = rotation;
				go_forward = forward;
				go_backward = backward;
				go_left = left;
				go_right = right;
			}
			break;
		case 4:
			with (obj_playerinit_physics.blue2){
				phy_angular_velocity = angular_velocity;
				phy_linear_velocity_x = linear_velocity_x;
				phy_linear_velocity_y = linear_velocity_y;
				phy_position_x = position_x;
				phy_position_y = position_y;
				phy_rotation = rotation;
				go_forward = forward;
				go_backward = backward;
				go_left = left;
				go_right = right;
			}
			break;
	}
}
