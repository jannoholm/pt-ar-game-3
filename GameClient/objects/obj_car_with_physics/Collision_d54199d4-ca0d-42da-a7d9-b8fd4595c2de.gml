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

// Create medium size explosion using built-in particles (and smaller one inside for nicer effect)
effect_create_above(ef_explosion, other.x, other.y, 1, c_orange);
effect_create_above(ef_explosion, other.x, other.y, 0, make_color_rgb(176,64,16));

// Play explosion sound
audio_play_sound(snd_missile_explosion, 5, false);