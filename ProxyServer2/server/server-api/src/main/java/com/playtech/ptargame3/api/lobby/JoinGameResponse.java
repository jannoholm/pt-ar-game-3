package com.playtech.ptargame3.api.lobby;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;

public class JoinGameResponse extends AbstractResponse {

    private Team team = Team.RED;
    private byte positionInTeam = 1;

    public JoinGameResponse(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        team = Team.values()[messageData.get()];
        positionInTeam = messageData.get();
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.put((byte)team.ordinal());
        messageData.put(positionInTeam);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", clientId=").append(getTeam());
        s.append(", name=").append(getPositionInTeam());
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public byte getPositionInTeam() {
        return positionInTeam;
    }

    public void setPositionInTeam(byte positionInTeam) {
        this.positionInTeam = positionInTeam;
    }
}
