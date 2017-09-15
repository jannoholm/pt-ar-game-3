enum TeamColor {
	RED,
	BLUE
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

// name
client_name="";

colliding=0;
damaged=0;
shoot=false;
shoot_delay=0;
boost=false;
boost_power=180;
bullets=ds_list_create();

// Wheel offsets
rearTireOffsetX = -40;
rearLeftTireOffsetY = 20;
rearRightTireOffsetY = -20;

// Since trailing script does not support multiple trails for the same object, fake ones need to be created
rearLeftTire = instance_create_layer(x + rearTireOffsetX, y + rearLeftTireOffsetY, "car", obj_car_tire);
rearRightTire = instance_create_layer(x + rearTireOffsetX, y + rearRightTireOffsetY, "car", obj_car_tire);

with ( rearLeftTire) {
	trail_init();
}

with ( rearRightTire) {
	trail_init();
}