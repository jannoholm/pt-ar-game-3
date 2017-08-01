package com.playtech.ptargame3.api.general;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;

public class PingResponse extends AbstractMessage {

    public PingResponse(MessageHeader header) {
        super(header);
    }

}