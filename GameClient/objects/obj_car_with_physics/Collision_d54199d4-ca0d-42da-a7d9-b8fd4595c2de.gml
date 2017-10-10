if (remote_control==false) {
	damaged=30*2; // 2 sec
	damage_direction=choose(-1, 1);
	damage_turn=choose(-1, 1);
}
with (other.shooter) {
	score_bullet_hits=score_bullet_hits+1;
}
with (other) {
	instance_destroy();	
}
