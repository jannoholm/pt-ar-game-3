package com.playtech.ptargame3.api.game;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.HexUtil;

import java.nio.ByteBuffer;

public class GameUpdateMessage extends AbstractMessage {

    private byte[] content;

    public GameUpdateMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", content=").append(HexUtil.toHex(content));
    }

    @Override
    public void parse(ByteBuffer messageData) {
        int size = messageData.remaining();
        content = new byte[size];
        messageData.get(content);
    }

    @Override
    public void format(ByteBuffer messageData) {
        if (content != null) {
            messageData.put(content);
        }
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
