package com.playtech.ptargame3.api;


import com.playtech.ptargame3.common.message.MessageHeader;

public abstract class AbstractRequest extends AbstractMessage {
    public AbstractRequest(MessageHeader header) {
        super(header);
    }
}
