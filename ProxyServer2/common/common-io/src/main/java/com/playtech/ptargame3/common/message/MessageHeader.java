package com.playtech.ptargame3.common.message;


public interface MessageHeader {
    int getMessageType();
    long getMessageId();
    String getClientId();
    void setClientId(String clientId);
}
