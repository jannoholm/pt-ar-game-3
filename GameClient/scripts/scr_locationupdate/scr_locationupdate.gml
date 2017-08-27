var buffer = argument[0];

var gameid=buffer_read(buffer, buffer_string); 
var control_length=buffer_read(buffer, buffer_s32); 

var i;
for (i=0; i<4; ++i) {
	var carid=buffer_read(buffer, buffer_s8);
	var position_x=buffer_read(buffer, buffer_f32);
	var position_y=buffer_read(buffer, buffer_f32);
	var rotation=buffer_read(buffer, buffer_f32);
	switch (carid) {
		case 100: // ball
			with (obj_ball){
				// TODO
			}
			break;
		case 1:
			with (obj_playerinit_physics.red1) {
				// TODO
			}
			break;
		case 2:
			with (obj_playerinit_physics.red2){
				// TODO
			}
			break;
		case 3:
			with (obj_playerinit_physics.blue1){
				// TODO
			}
			break;
		case 4:
			with (obj_playerinit_physics.blue2){
				// TODO
			}
			break;
	}
}
