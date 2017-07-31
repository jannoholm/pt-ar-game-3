package server.message.lobby;

import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractRequest;

import java.nio.ByteBuffer;


public class GetDetailedGameInfoRequest extends AbstractRequest {
    private String gameId;
    public GetDetailedGameInfoRequest(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", ").append(gameId);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        gameId = StringUtil.readUTF8String(messageData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameId, messageData);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
