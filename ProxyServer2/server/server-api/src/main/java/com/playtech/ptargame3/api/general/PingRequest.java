package com.playtech.ptargame3.api.general;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;

public class PingRequest extends AbstractMessage {

    public PingRequest(MessageHeader header) {
        super(header);
    }

}
