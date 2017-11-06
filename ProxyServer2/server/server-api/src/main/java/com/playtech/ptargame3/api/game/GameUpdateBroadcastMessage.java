package com.playtech.ptargame3.api.game;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.HexUtil;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class GameUpdateBroadcastMessage extends AbstractMessage {

    private String gameId;
    private byte[] broadcardContent;

    public GameUpdateBroadcastMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", content=").append(HexUtil.toHex(broadcardContent));
    }

    @Override
    public void parse(ByteBuffer messageData) {
        gameId = StringUtil.readUTF8String(messageData);
        int size = messageData.getInt();
        broadcardContent = new byte[size];
        messageData.get(broadcardContent);
    }

    @Override
    public void format(ByteBuffer messageData) {
        StringUtil.writeUTF8String(gameId, messageData);
        if (broadcardContent != null) {
            messageData.putInt(broadcardContent.length);
            messageData.put(broadcardContent);
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

    public byte[] getBroadcardContent() {
        return broadcardContent;
    }

    public void setBroadcardContent(byte[] broadcardContent) {
        this.broadcardContent = broadcardContent;
    }
}
