package com.playtech.ptargame3.common.message;

import java.nio.ByteBuffer;
import java.util.List;

public interface MessageParser {

    Message parseMessage(List<ByteBuffer> messageBuffer);

    void formatMessage(Message message, ByteBuffer targetBuffer);

    long generateId();

    <T extends Message> T createMessage(Class<T> messageClass);

    <T extends Message, R extends Message> R createResponse(T request, Class<R> responseClass);

}
