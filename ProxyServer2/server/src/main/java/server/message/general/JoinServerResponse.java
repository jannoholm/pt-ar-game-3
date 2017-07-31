package server.message.general;


import common.message.MessageHeader;
import server.message.AbstractMessage;
import server.message.AbstractResponse;

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
