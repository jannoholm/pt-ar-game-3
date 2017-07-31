package server.message.game;


import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractResponse;

import java.nio.ByteBuffer;

public class HostGameResponse extends AbstractResponse {

    private String gameId;

    public HostGameResponse(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(getGameId());
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.gameId = StringUtil.readUTF8String(messageData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(this.gameId, messageData);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
