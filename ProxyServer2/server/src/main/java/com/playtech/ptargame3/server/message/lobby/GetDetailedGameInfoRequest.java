package com.playtech.ptargame3.server.message.lobby;

import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.message.AbstractRequest;

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
