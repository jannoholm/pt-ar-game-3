package com.playtech.ptargame3.api.camera;


import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.HexUtil;

import java.nio.ByteBuffer;

public class LocationNotificationMessage extends AbstractMessage {

    private byte[] locationData;

    public LocationNotificationMessage(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        int size = messageData.getInt();
        locationData = new byte[size];
        messageData.get(locationData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        if (locationData != null) {
            messageData.putInt(locationData.length);
            messageData.put(locationData);
        } else {
            messageData.putInt(0);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", controlData=").append(HexUtil.toHex(locationData));
    }
}
