// Move car
var leftWheelPower = argument0 * 2;
var rightWheelPower = argument1 * 2;

physics_apply_local_force(rearTireOffsetX, rearLeftTireOffsetY, leftWheelPower*10000, 0);
physics_apply_local_force(rearTireOffsetX, rearRightTireOffsetY, rightWheelPower*10000, 0);
