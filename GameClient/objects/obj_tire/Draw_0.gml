draw_self();
scr_draw_trail(8,8,0,-1,0,1);
if (dodraw == 1) {
draw_text(0, 0, "nx/ny: " + string(nx) + "/" + string(ny));
draw_text(0, 20, "Spd: " + string(phy_speed));
draw_text(0, 40, "Mass: " + string(phy_mass));
draw_text(0, 60, "dot: " + string(dot_product(nx, ny, phy_linear_velocity_x * world_size, phy_linear_velocity_y * world_size)));
draw_text(0, 80, "vx: " + string(phy_linear_velocity_x));
draw_text(0, 100, "vy: " + string(phy_linear_velocity_y));
}

