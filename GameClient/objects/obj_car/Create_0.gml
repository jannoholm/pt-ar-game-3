// setup wheels and car in general
fl_tire = instance_create_layer(x+32, y-28, "car", obj_tire);
fl_j = physics_joint_revolute_create(id, fl_tire, fl_tire.x, fl_tire.y, -25, 25, 1, 2, 0, 1, 0);
physics_joint_enable_motor(fl_j, 0);

fr_tire = instance_create_layer(x+32, y+28, "car", obj_tire);
fr_j = physics_joint_revolute_create(id, fr_tire, fr_tire.x, fr_tire.y, -25, 25, 1, 2, 0, 1, 0);
physics_joint_enable_motor(fr_j, 0);

bl_tire = instance_create_layer(x-32, y-28, "car", obj_tire);
bl_j = physics_joint_revolute_create(id, bl_tire, bl_tire.x, bl_tire.y, 0, 0, 1, 0, 0, 0, 0);

br_tire = instance_create_layer(x-32, y+28, "car", obj_tire);
br_j = physics_joint_revolute_create(id, br_tire, br_tire.x, br_tire.y, 0, 0, 1, 0, 0, 0, 0);

// constants
tire_dire=0;
tire_maxdire=25;
tire_mixdire=-25;
trn_speed=3;
world_size=0.025

// debug
dodraw=0;

// controls
KEY_FORWARD=vk_up;
KEY_BACKWARD=vk_down;
KEY_LEFT=vk_left;
KEY_RIGHT=vk_right;
