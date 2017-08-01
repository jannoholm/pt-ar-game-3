package com.playtech.ptargame3.server.message;


import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class MessageHeaderImpl implements MessageHeader {
    private long messageId;
    private int messageType;
    private String clientId;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes);

        // read actual data
        messageType = messageData.getInt();
        messageId = messageData.getLong();
        clientId = StringUtil.readUTF8String(messageData);
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        // write data
        messageData.putInt(messageType);
        messageData.putLong(messageId);
        StringUtil.writeUTF8String(getClientId(), messageData);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    public void toStringImpl(StringBuilder s) {
        s.append(getMessageId());
        if (!StringUtil.isNull(getClientId())) {
            s.append("-").append(getClientId());
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        toStringImpl(s);
        return s.toString();
    }

    public long getMessageId() {
        return messageId;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
