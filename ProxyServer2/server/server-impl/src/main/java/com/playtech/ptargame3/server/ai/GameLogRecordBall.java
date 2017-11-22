package com.playtech.ptargame3.server.ai;

import java.nio.ByteBuffer;

public class GameLogRecordBall {

    private boolean exists;
    private float phy_angular_velocity;
    private float phy_linear_velocity_x;
    private float phy_linear_velocity_y;
    private float phy_speed_x;
    private float phy_speed_y;
    private float phy_position_x;
    private float phy_position_y;
    private float phy_rotation;

    public void parse(ByteBuffer messageData) {
        exists = (messageData.get() == 1);
        phy_angular_velocity=messageData.getFloat();
        phy_linear_velocity_x=messageData.getFloat();
        phy_linear_velocity_y=messageData.getFloat();
        phy_speed_x=messageData.getFloat();
        phy_speed_y=messageData.getFloat();
        phy_position_x=messageData.getFloat();
        phy_position_y=messageData.getFloat();
        phy_rotation=messageData.getFloat();
    }

    public boolean isExists() {
        return exists;
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
}
