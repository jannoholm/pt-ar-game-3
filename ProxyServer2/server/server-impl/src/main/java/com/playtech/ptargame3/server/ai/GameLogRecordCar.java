package com.playtech.ptargame3.server.ai;

public class GameLogRecordCar {

    private final int phy_angular_velocity;
    private final int phy_linear_velocity_x;
    private final int phy_linear_velocity_y;
    private final int phy_speed_x;
    private final int phy_speed_y;
    private final int phy_position_x;
    private final int phy_position_y;
    private final int phy_rotation;
    private final byte go_move;
    private final byte go_turn;
    private final byte colliding;
    private final int damaged;
    private final int shoot_delay;
    private final byte boost;
    private final int boost_power;

    public GameLogRecordCar(int phy_angular_velocity, int phy_linear_velocity_x, int phy_linear_velocity_y, int phy_speed_x, int phy_speed_y, int phy_position_x, int phy_position_y, int phy_rotation, byte go_move, byte go_turn, byte colliding, int damaged, int shoot_delay, byte boost, int boost_power) {
        this.phy_angular_velocity = phy_angular_velocity;
        this.phy_linear_velocity_x = phy_linear_velocity_x;
        this.phy_linear_velocity_y = phy_linear_velocity_y;
        this.phy_speed_x = phy_speed_x;
        this.phy_speed_y = phy_speed_y;
        this.phy_position_x = phy_position_x;
        this.phy_position_y = phy_position_y;
        this.phy_rotation = phy_rotation;
        this.go_move = go_move;
        this.go_turn = go_turn;
        this.colliding = colliding;
        this.damaged = damaged;
        this.shoot_delay = shoot_delay;
        this.boost = boost;
        this.boost_power = boost_power;
    }

    public int getPhy_angular_velocity() {
        return phy_angular_velocity;
    }

    public int getPhy_linear_velocity_x() {
        return phy_linear_velocity_x;
    }

    public int getPhy_linear_velocity_y() {
        return phy_linear_velocity_y;
    }

    public int getPhy_speed_x() {
        return phy_speed_x;
    }

    public int getPhy_speed_y() {
        return phy_speed_y;
    }

    public int getPhy_position_x() {
        return phy_position_x;
    }

    public int getPhy_position_y() {
        return phy_position_y;
    }

    public int getPhy_rotation() {
        return phy_rotation;
    }

    public byte getGo_move() {
        return go_move;
    }

    public byte getGo_turn() {
        return go_turn;
    }

    public byte getColliding() {
        return colliding;
    }

    public int getDamaged() {
        return damaged;
    }

    public int getShoot_delay() {
        return shoot_delay;
    }

    public byte getBoost() {
        return boost;
    }

    public int getBoost_power() {
        return boost_power;
    }
}
