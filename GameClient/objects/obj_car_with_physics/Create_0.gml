// setup wheels and car in general
var carLayer = layer_create(0);
var axelFicture = physics_fixture_create();

rearLeftTire = instance_create_layer(x-40, y+20, carLayer, obj_car_tire_back);
rearRightTire = instance_create_layer(x-40, y-20, carLayer, obj_car_tire_back);

physics_joint_weld_create(id, rearLeftTire, x-40, y+20, 0, 0, 0, true);
physics_joint_weld_create(id, rearRightTire, x-40, y-20, 0, 0, 0, true);


// debug
dodraw=0;

// controls
remote_control=true;
go_forward=false;
go_backward=false;
go_left=false;
go_right=false;

// name
client_name="";
/*
KEY_FORWARD=vk_up;
KEY_BACKWARD=vk_down;
KEY_LEFT=vk_left;
KEY_RIGHT=vk_right;
*/