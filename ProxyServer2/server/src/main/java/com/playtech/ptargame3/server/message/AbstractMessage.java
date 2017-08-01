package com.playtech.ptargame3.server.message;


import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.message.MessageHeader;

public abstract class AbstractMessage implements Message {
    private final MessageHeader header;

    public AbstractMessage(MessageHeader header) {
        this.header = header;
    }

    @Override
    public MessageHeader getHeader() {
        return header;
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
