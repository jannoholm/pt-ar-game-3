package com.playtech.ptargame3.server.ai;

import java.nio.ByteBuffer;

public class GameLogRecordBullet {

    private float phy_position_x;
    private float phy_position_y;
    private float phy_rotation;

    public void parse(ByteBuffer messageData) {
        phy_position_x=messageData.getFloat();
        phy_position_y=messageData.getFloat();
        phy_rotation=messageData.getFloat();
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
