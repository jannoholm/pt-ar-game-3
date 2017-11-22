package com.playtech.ptargame3.server.ai;

import java.nio.ByteBuffer;

public class GameLogRecordCar {

    private final byte carId;
    private float phy_angular_velocity;
    private float phy_linear_velocity_x;
    private float phy_linear_velocity_y;
    private float phy_speed_x;
    private float phy_speed_y;
    private float phy_position_x;
    private float phy_position_y;
    private float phy_rotation;
    private byte go_move;
    private byte go_turn;
    private byte colliding;
    private short damaged;
    private short shoot_delay;
    private byte boost;
    private short boost_power;

    public GameLogRecordCar(byte carId) {
        this.carId = carId;
    }

    public void parse(ByteBuffer messageData) {
        phy_angular_velocity=messageData.getFloat();
        phy_linear_velocity_x=messageData.getFloat();
        phy_linear_velocity_y=messageData.getFloat();
        phy_speed_x=messageData.getFloat();
        phy_speed_y=messageData.getFloat();
        phy_position_x=messageData.getFloat();
        phy_position_y=messageData.getFloat();
        phy_rotation=messageData.getFloat();
        go_move=messageData.get();
        go_turn=messageData.get();
        colliding=messageData.get();
        damaged=messageData.getShort();
        shoot_delay=messageData.getShort();
        boost=messageData.get();
        boost_power=messageData.getShort();
    }

    public float getPhy_angular_velocity() {
        return phy_angular_velocity;
    }

    public float getPhy_linear_velocity_x() {
        return phy_linear_velocity_x;
    }

    public float getPhy_linear_velocity_y() {
        return phy_linear_velocity_y;
    }

    public float getPhy_speed_x() {
        return phy_speed_x;
    }

    public float getPhy_speed_y() {
        return phy_speed_y;
    }

    public float getPhy_position_x() {
        return phy_position_x;
    }

    public float getPhy_position_y() {
        return phy_position_y;
    }

    public float getPhy_rotation() {
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
