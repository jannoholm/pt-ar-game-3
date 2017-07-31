package server.message;


import common.message.MessageHeader;

import java.nio.ByteBuffer;

public abstract class AbstractRequest extends AbstractMessage {
    public AbstractRequest(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
    }

    @Override
    public void parse(ByteBuffer messageData) {
    }

    @Override
    public void format(ByteBuffer messageData) {
    }
}
