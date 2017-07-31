package server.message.general;

import common.message.MessageHeader;
import server.message.AbstractMessage;

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
