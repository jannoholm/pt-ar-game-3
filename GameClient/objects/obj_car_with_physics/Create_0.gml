rearLeftTire = instance_create_layer(x-20, y+26, "car", obj_tire);
physics_joint_weld_create(id, rearLeftTire, x-40, y+20, 0, 0, 0, true);

rearRightTire = instance_create_layer(x-20, y-26, "car", obj_tire);
physics_joint_weld_create(id, rearRightTire, x-40, y-20, 0, 0, 0, true);


// constants
tire_dire=0;
tire_maxdire=35;
tire_mixdire=-35;
trn_speed=3;
world_size=0.025

// debug
dodraw=0;

// controls
remote_control=true;
go_move=0;
go_turn=0;
/*
go_forward=false;
go_backward=false;
go_left=false;
go_right=false;
*/
// name
client_name="";
