enum TeamColor {
	RED,
	BLUE
}

enum ResetPhase {
	GO_TO_MID,
	ROTATE_AT_MID,
	BACK_UP_TO_START,
	RESET_COMPLETE
}


teamColor = TeamColor.RED;

world_size=0.025

// debug
dodraw=0;

// Resetting position variables
atPosition = true;
currentResetPhase = ResetPhase.RESET_COMPLETE;
initialPosX = x;
initialPoxY = y;
initialRotation = phy_rotation;

// controls
remote_control=true;
go_move=0;
go_turn=0;

// enable keyboard in table
keyboard_enabler=-1;
keyboard_show=false;

// name
client_name="";

// game elements
highlight=0;
colliding=0;
damaged=0;
damage_direction=0;
damage_turn=0;
shoot=false;
shoot_delay=0;
shoot_interval=10*room_speed; // every 10s
boost=false;
boost_power=6*room_speed;
boost_max=30*room_speed; // 1.6 sec
bullets=ds_list_create();
ready=0;

// score elements
score_last_touch=current_time;
score_goals=0;
score_bullet_hits=0;
score_boost_touches=0;

// user select in table
show_user_select=false;
show_user_select_id=0;
show_user_select_name="";
show_user_select_name_prev="";
show_user_select_name_next="";

show_user_select_scroll_cooldown=5;
go_move_prev=0;

// Wheel offsets
rearTireOffsetX = -50;
rearLeftTireOffsetY = 35;
rearRightTireOffsetY = -35;

// Since trailing script does not support multiple trails for the same object, fake ones need to be created
rearLeftTire = instance_create_layer(x + rearTireOffsetX, y + rearLeftTireOffsetY, "car", obj_car_tire);
rearRightTire = instance_create_layer(x + rearTireOffsetX, y + rearRightTireOffsetY, "car", obj_car_tire);

with ( rearLeftTire) {
	trail_init();
}

with ( rearRightTire) {
	trail_init();
}