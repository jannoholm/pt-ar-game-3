package com.playtech.ptargame3.server.message.general;

import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.server.message.AbstractMessage;

import java.nio.ByteBuffer;

public class PingResponse extends AbstractMessage {

    public PingResponse(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
    }

    @Override
    public void format(ByteBuffer messageData) {
    }

}
