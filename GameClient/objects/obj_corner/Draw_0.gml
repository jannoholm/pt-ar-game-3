	
if( ballInArea ) {
	draw_set_color(c_red);
} else {
	draw_set_color(c_white);	
}

// draw_rectangle(bbox_left, bbox_top, bbox_right, bbox_bottom, true);
// draw_circle(x, y, senseBallRadius, true)
var r = senseCarsRadius;
// draw_ellipse( x-r, y-r, x+r, y+r, true);


with(obj_ball) {
	var blastPowerX = x + lengthdir_x(400, point_direction(x,y,room_width/2,room_height/2));
	var blastPowerY = y + lengthdir_y(400, point_direction(x,y,room_width/2,room_height/2));

	//draw_circle(blastPowerX, blastPowerY, 10, false);
}



