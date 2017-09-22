package com.playtech.ptargame3.api.general;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class JoinServerRequest extends AbstractRequest {

    public enum ClientType {
        TABLE,
        CAMERA,
        GAME_CLIENT,
        PROXY,
        CAR_CONTROL
    }

    private String name;
    private String email;
    private ClientType clientType = ClientType.GAME_CLIENT;

    public JoinServerRequest(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.name = StringUtil.readUTF8String(messageData);
        this.email = StringUtil.readUTF8String(messageData);
        this.clientType = ClientType.values()[messageData.get()];
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(name, messageData);
        StringUtil.writeUTF8String(email, messageData);
        messageData.put((byte) clientType.ordinal());
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", name=").append(getName());
        s.append(", email=").append(getEmail());
        s.append(", type=").append(getClientType());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
