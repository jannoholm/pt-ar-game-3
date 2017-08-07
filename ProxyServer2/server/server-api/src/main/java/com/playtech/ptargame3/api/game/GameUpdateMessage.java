package com.playtech.ptargame3.api.game;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.HexUtil;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GameUpdateMessage extends AbstractMessage {

    private String gameId;
    private byte[] content;

    public GameUpdateMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", content=").append(HexUtil.toHex(content));
    }

    @Override
    public void parse(ByteBuffer messageData) {
        gameId = StringUtil.readUTF8String(messageData);
        int size = messageData.getInt();
        content = new byte[size];
        messageData.get(content);
    }

    @Override
    public void format(ByteBuffer messageData) {
        StringUtil.writeUTF8String(gameId, messageData);
        if (content != null) {
            messageData.putInt(content.length);
            messageData.put(content);
        } else {
            messageData.putInt(0);
        }
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
