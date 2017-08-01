package com.playtech.ptargame3.api;


import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;

public abstract class AbstractMessage implements Message {
    private final MessageHeader header;

    public AbstractMessage(MessageHeader header) {
        this.header = header;
    }

    @Override
    public MessageHeader getHeader() {
        return header;
    }

    @Override
    public void parse(ByteBuffer messageData) {
    }

    @Override
    public void format(ByteBuffer messageData) {
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("h=");
        if (header instanceof MessageHeaderImpl) {
            MessageHeaderImpl headerImpl = (MessageHeaderImpl)header;
            headerImpl.toStringImpl(s);
        } else {
            s.append(header.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getClass().getSimpleName()).append("(");
        toStringImpl(s);
        s.append(")");
        return s.toString();
    }
}
