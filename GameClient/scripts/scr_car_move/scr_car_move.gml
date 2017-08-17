var leftWheelPower = argument0;
var rightWheelPower = argument1;


with(rearLeftTire){
	physics_apply_local_force(0, 0, leftWheelPower*1000, 0);
}

with(rearRightTire){
	physics_apply_local_force(0, 0, rightWheelPower*1000, 0);
}
