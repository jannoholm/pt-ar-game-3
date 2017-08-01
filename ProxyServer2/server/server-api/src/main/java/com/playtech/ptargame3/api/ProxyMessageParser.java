package com.playtech.ptargame3.api;

import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.message.MessageFactory;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.util.IdGenerator;

import java.nio.ByteBuffer;
import java.util.List;

public class ProxyMessageParser implements MessageParser {

    private final MessageFactory messageFactory;
    private final IdGenerator idGenerator = new IdGenerator(10000);

    public ProxyMessageParser(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public Message parseMessage(List<ByteBuffer> messageBuffer) {
        // wrap to single buffer
        ByteBuffer single;
        if (messageBuffer.size()>1) {
            // calculate total size
            int totalBytes = 0;
            for (ByteBuffer buffer : messageBuffer) {
                totalBytes += buffer.remaining();
            }
            single = ByteBuffer.allocate(totalBytes);
            for (ByteBuffer buffer : messageBuffer) {
                single.put(buffer);
            }
            single.flip();
        } else {
            single = messageBuffer.get(0);
        }

        // get message type
        MessageHeaderImpl header = new MessageHeaderImpl();
        header.parse(single);

        Message message = this.messageFactory.createMessage(header);
        message.parse(single);
        return message;
    }

    @Override
    public void formatMessage(Message message, ByteBuffer targetBuffer) {
        if (message.getHeader() instanceof MessageHeaderImpl) {
            MessageHeaderImpl header = (MessageHeaderImpl) message.getHeader();
            header.format(targetBuffer);
        } else {
            throw new IllegalArgumentException("Invalid message for this server: " + message);
        }
        message.format(targetBuffer);
    }

    @Override
    public long generateId() {
        return this.idGenerator.generateId();
    }

    @Override
    public <T extends Message> T createMessage(Class<T> messageClass) {
        return createMessage(messageClass, generateId());
    }

    @Override
    public <T extends Message, R extends Message> R createResponse(T request, Class<R> responseClass) {
        return createMessage(responseClass, request.getHeader().getMessageId());
    }

    private <T extends Message> T createMessage(Class<T> messageClass, long messageId) {
        MessageHeaderImpl header = new MessageHeaderImpl();
        header.setMessageId(messageId);
        header.setMessageType(this.messageFactory.getMessageType(messageClass));
        return this.messageFactory.createMessage(header);
    }
}
