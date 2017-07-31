package server.message.game;


import common.util.StringUtil;

import java.nio.ByteBuffer;

public class GameUpdateCar {

    private int positionX;
    private int positionY;
    private double angle;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes);

        // read structure data
        positionX = messageData.getInt();
        positionY = messageData.getInt();
        angle = messageData.getDouble();
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        messageData.putInt(positionX);
        messageData.putInt(positionY);
        messageData.putDouble(angle);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("positionX=").append(positionX);
        s.append(", positionY=").append(positionY);
        s.append(", angle=").append(angle);
    }

}
