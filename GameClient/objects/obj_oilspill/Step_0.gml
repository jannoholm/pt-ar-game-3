if (obj_playerinit_physics.red1.remote_control==false) {
	obj_playerinit_physics.red1.colliding=obj_playerinit_physics.red1.colliding+collision_ellipse( x-60, y-50, x+60, y+50, obj_playerinit_physics.red1, false, true );
}
if (obj_playerinit_physics.red2.remote_control==false) {
	obj_playerinit_physics.red2.colliding=obj_playerinit_physics.red2.colliding+collision_ellipse( x-60, y-50, x+60, y+50, obj_playerinit_physics.red2, false, true );
}
if (obj_playerinit_physics.blue1.remote_control==false) {
	obj_playerinit_physics.blue1.colliding=obj_playerinit_physics.blue1.colliding+collision_ellipse( x-60, y-50, x+60, y+50, obj_playerinit_physics.blue1, false, true );
}
if (obj_playerinit_physics.blue2.remote_control==false) {
	obj_playerinit_physics.blue2.colliding=obj_playerinit_physics.blue2.colliding+collision_ellipse( x-60, y-50, x+60, y+50, obj_playerinit_physics.blue2, false, true );
}