package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.lobby.Team;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;

public class SetUserInCarRequest extends AbstractRequest {

    private int userId;
    private Team team;
    private byte positionInTeam;

    public SetUserInCarRequest(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        userId = messageData.getInt();
        team = Team.values()[messageData.get()];
        positionInTeam = messageData.get();
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(userId);
        messageData.put((byte)team.ordinal());
        messageData.put(positionInTeam);
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", userId=").append(userId);
        s.append(", team=").append(team);
        s.append(", positionInTeam=").append(positionInTeam);
    }

}
