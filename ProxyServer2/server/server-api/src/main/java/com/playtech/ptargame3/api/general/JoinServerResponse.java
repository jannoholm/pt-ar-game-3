package com.playtech.ptargame3.api.general;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;

public class JoinServerResponse extends AbstractResponse {

    public JoinServerResponse(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
    }

}
