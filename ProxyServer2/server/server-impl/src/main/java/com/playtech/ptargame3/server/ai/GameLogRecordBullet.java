package com.playtech.ptargame3.server.ai;

public class GameLogRecordBullet {

    private final int phy_position_x;
    private final int phy_position_y;
    private final int phy_rotation;

    public GameLogRecordBullet(int phy_position_x, int phy_position_y, int phy_rotation) {
        this.phy_position_x = phy_position_x;
        this.phy_position_y = phy_position_y;
        this.phy_rotation = phy_rotation;
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
}
