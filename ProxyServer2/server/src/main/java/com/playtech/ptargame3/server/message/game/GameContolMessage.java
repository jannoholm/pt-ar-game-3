package com.playtech.ptargame3.server.message.game;

import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.server.message.AbstractMessage;

import java.nio.ByteBuffer;

public class GameContolMessage extends AbstractMessage {

    private byte leftRight;
    private byte forwardBackward;

    public GameContolMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", leftRight=").append(getLeftRight());
        s.append(", forwardBackward=").append(getForwardBackward());
    }

    @Override
    public void parse(ByteBuffer messageData) {
        this.leftRight = messageData.get();
        this.forwardBackward = messageData.get();
    }

    @Override
    public void format(ByteBuffer messageData) {
        messageData.put(this.leftRight);
        messageData.put(this.forwardBackward);
    }

    public byte getLeftRight() {
        return leftRight;
    }

    public void setLeftRight(byte leftRight) {
        this.leftRight = leftRight;
    }

    public byte getForwardBackward() {
        return forwardBackward;
    }

    public void setForwardBackward(byte forwardBackward) {
        this.forwardBackward = forwardBackward;
    }
}
