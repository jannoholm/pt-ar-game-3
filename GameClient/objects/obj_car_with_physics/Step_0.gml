//var dire = (keyboard_check(KEY_RIGHT)-keyboard_check(KEY_LEFT))*trn_speed;
var dire = (clamp(go_right,0,1)-clamp(go_left,0,1))*trn_speed;

tire_dire = clamp(tire_dire + dire, tire_mixdire, tire_maxdire);
if (dire == 0) {
	var s = sign(tire_dire);
	tire_dire = max(abs(tire_dire)-trn_speed,0)*s;
}

physics_joint_set_value(fr_j, phy_joint_upper_angle_limit, tire_dire);
physics_joint_set_value(fr_j, phy_joint_lower_angle_limit, tire_dire);

physics_joint_set_value(fl_j, phy_joint_upper_angle_limit, tire_dire);
physics_joint_set_value(fl_j, phy_joint_lower_angle_limit, tire_dire);

var tmp = phy_mass;
//if keyboard_check(KEY_FORWARD) {
if (go_forward) {
	with(fl_tire) {
		physics_apply_local_force(0, 0, tmp, 0);
	}
	with(fr_tire) {
		physics_apply_local_force(0, 0, tmp, 0);
	}
}
tmp = -1*phy_mass;
//if keyboard_check(KEY_BACKWARD) {
if (go_backward) {
	with(fl_tire) {
		physics_apply_local_force(0, 0, tmp, 0);
	}
	with(fr_tire) {
		physics_apply_local_force(0, 0, tmp, 0);
	}
}