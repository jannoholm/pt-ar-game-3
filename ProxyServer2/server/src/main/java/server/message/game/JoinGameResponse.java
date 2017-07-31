package server.message.game;


import common.message.MessageHeader;
import server.message.AbstractResponse;

import java.nio.ByteBuffer;

public class JoinGameResponse extends AbstractResponse {

    public JoinGameResponse(MessageHeader header) {
        super(header);
    }

}
