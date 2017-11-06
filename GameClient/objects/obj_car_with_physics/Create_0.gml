enum TeamColor {
	RED,
	BLUE
}

enum PlayerType {
	PLAYER,
	AI_CHASER,
	AI_DEFENDER
}

enum ResetPhase {
	GO_TO_MID,
	ROTATE_AT_MID,
	BACK_UP_TO_START,
	RESET_COMPLETE
}

teamColor = TeamColor.RED;
playerType = PlayerType.PLAYER;

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
aiLeftWheelPower = 0;
aiRightWheelPower = 0;

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
boost_flame_animated = false;
boost_power=6*room_speed;
boost_max=30*room_speed; // 1.6 sec
bullets=ds_list_create();
ready=0;
ready_delay=0;
ready_interval=room_speed;

// score elements
score_last_touch=current_time;
score_last_touch_interval=current_time;
score_goals=0;
score_bullet_hits=0;
score_ball_touches=0;
score_boost_touches=0;
score_gamescore=0;
score_eloRating=0;
score_leaderboardPosition=0;

// user select in table
show_user_select=false;
show_user_select_id=0;
show_user_select_name="";
show_user_select_name_prev1="";
show_user_select_name_prev2="";
show_user_select_name_next1="";
show_user_select_name_next2="";

show_user_select_scroll_cooldown=5;
show_user_select_scroll_speedup=0;
go_move_prev=0;

// Boost flame offset
boostFlameOffsetX = -50;

// Wheel offsets
rearTireOffsetX = -50;
rearLeftTireOffsetY = 35;
rearRightTireOffsetY = -35;


// Sound related
boost_flame_sound = noone;
oilspill_tire_sound = noone;

// Since trailing script does not support multiple trails for the same object, fake ones need to be created
rearLeftTire = instance_create_layer(x + rearTireOffsetX, y + rearLeftTireOffsetY, "car", obj_car_tire);
rearRightTire = instance_create_layer(x + rearTireOffsetX, y + rearRightTireOffsetY, "car", obj_car_tire);

with ( rearLeftTire) {
	trail_init();
}

with ( rearRightTire) {
	trail_init();
}