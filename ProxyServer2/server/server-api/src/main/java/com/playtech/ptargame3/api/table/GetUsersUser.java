package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GetUsersUser {

    private int id;
    private String name;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // read structure data
        id=messageData.getInt();
        name= StringUtil.readUTF8String(messageData);
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        messageData.putInt(id);
        StringUtil.writeUTF8String(name, messageData);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("id=").append(getId());
        s.append(", name=").append(getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
