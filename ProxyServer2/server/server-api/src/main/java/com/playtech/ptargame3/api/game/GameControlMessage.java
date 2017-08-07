package com.playtech.ptargame3.api.game;

import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.util.HexUtil;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GameControlMessage extends AbstractMessage {

    private String gameId;
    private String controlClientId;
    private byte[] controlData;

    public GameControlMessage(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", controlClientId=").append(controlClientId);
        s.append(", controlData=").append(HexUtil.toHex(controlData));
    }

    @Override
    public void parse(ByteBuffer messageData) {
        gameId = StringUtil.readUTF8String(messageData);
        controlClientId = StringUtil.readUTF8String(messageData);
        int size = messageData.getInt();
        controlData = new byte[size];
        messageData.get(controlData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        StringUtil.writeUTF8String(gameId, messageData);
        StringUtil.writeUTF8String(controlClientId, messageData);
        if (controlData != null) {
            messageData.putInt(controlData.length);
            messageData.put(controlData);
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

    public String getControlClientId() {
        return controlClientId;
    }

    public void setControlClientId(String controlClientId) {
        this.controlClientId = controlClientId;
    }

    public byte[] getControlData() {
        return controlData;
    }

    public void setControlData(byte[] controlData) {
        this.controlData = controlData;
    }

}
