/// @description On click a random instance of any of the four objects that draw trails on this example will be created
var inst = instance_create(mouse_x,mouse_y,choose(obj_trail1,obj_trail2,obj_trail_ext1,obj_trail_ext2))
with(inst) {
color = make_colour_rgb(random(255),random(255),random(255))
slim = choose(1,0)
}


