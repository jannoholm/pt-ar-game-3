var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string); 
var control_length=buffer_read(buffer, buffer_s32); 

var i;
for (i=0; i<4; ++i) {
	var carid=buffer_read(buffer, buffer_s8);
	var x_pos=buffer_read(buffer, buffer_f32);
	var y_pos=buffer_read(buffer, buffer_f32);
	var angle=(-1)*buffer_read(buffer, buffer_f32);
	switch (carid) {
		case 100: // ball
			obj_ball.x=x_pos;
			obj_ball.y=y_pos;
			obj_ball.image_angle=angle;
			break;
		case 1:
			obj_playerinit_nophysics.red1.x=x_pos;
			obj_playerinit_nophysics.red1.y=y_pos;
			obj_playerinit_nophysics.red1.image_angle=angle;
			break;
		case 2:
			obj_playerinit_nophysics.red2.x=x_pos;
			obj_playerinit_nophysics.red2.y=y_pos;
			obj_playerinit_nophysics.red2.image_angle=angle;
			break;
		case 3:
			obj_playerinit_nophysics.blue1.x=x_pos;
			obj_playerinit_nophysics.blue1.y=y_pos;
			obj_playerinit_nophysics.blue1.image_angle=angle;
			break;
		case 4:
			obj_playerinit_nophysics.blue2.x=x_pos;
			obj_playerinit_nophysics.blue2.y=y_pos;
			obj_playerinit_nophysics.blue2.image_angle=angle;
			break;
	}
}
