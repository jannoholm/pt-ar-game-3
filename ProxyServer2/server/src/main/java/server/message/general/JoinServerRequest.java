package server.message.general;


import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractMessage;
import server.message.AbstractRequest;

import java.nio.ByteBuffer;

public class JoinServerRequest extends AbstractRequest {

    private String name;
    private String email;

    public JoinServerRequest(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.name = StringUtil.readUTF8String(messageData);
        this.email = StringUtil.readUTF8String(messageData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(name, messageData);
        StringUtil.writeUTF8String(email, messageData);
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", name=").append(getName());
        s.append(", email=").append(getEmail());
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
}
