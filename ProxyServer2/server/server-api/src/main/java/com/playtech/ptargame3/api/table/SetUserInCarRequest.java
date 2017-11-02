package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.lobby.Team;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class SetUserInCarRequest extends AbstractRequest {

    private String gameId;
    private int userId;
    private Team team;
    private byte positionInTeam;

    public SetUserInCarRequest(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        gameId = StringUtil.readUTF8String(messageData);
        userId = messageData.getInt();
        team = Team.values()[messageData.get()];
        positionInTeam = messageData.get();
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameId, messageData);
        messageData.putInt(userId);
        messageData.put((byte)team.ordinal());
        messageData.put(positionInTeam);
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", userId=").append(userId);
        s.append(", team=").append(team);
        s.append(", positionInTeam=").append(positionInTeam);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
