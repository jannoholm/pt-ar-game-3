/// @description Preventing the object to move outside the room
if (x < 0) || (x > room_width) hspeed = -hspeed;
if (y < 0) || (y > room_height) vspeed = -vspeed;

