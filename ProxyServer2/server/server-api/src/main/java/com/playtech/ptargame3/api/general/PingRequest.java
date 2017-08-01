package com.playtech.ptargame3.api.general;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;

public class PingRequest extends AbstractMessage {

    public PingRequest(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
    }

    @Override
    public void format(ByteBuffer messageData) {
    }

}
