package com.playtech.ptargame3.api.game;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.HexUtil;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GameUpdateBroadcastMessage extends AbstractMessage {

    private String gameId;
    private byte[] broadcastContent;

    public GameUpdateBroadcastMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", content=").append(HexUtil.toHex(broadcastContent));
    }

    @Override
    public void parse(ByteBuffer messageData) {
        gameId = StringUtil.readUTF8String(messageData);
        int size = messageData.getInt();
        broadcastContent = new byte[size];
        messageData.get(broadcastContent);
    }

    @Override
    public void format(ByteBuffer messageData) {
        StringUtil.writeUTF8String(gameId, messageData);
        if (broadcastContent != null) {
            messageData.putInt(broadcastContent.length);
            messageData.put(broadcastContent);
        } else {
            messageData.putInt(0);
        }
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public byte[] getBroadcastContent() {
        return broadcastContent;
    }

    public void setBroadcastContent(byte[] broadcastContent) {
        this.broadcastContent = broadcastContent;
    }
}
