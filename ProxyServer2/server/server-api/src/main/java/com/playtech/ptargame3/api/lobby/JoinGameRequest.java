package com.playtech.ptargame3.api.lobby;

import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class JoinGameRequest extends AbstractRequest {

    public enum Team {
        RANDOM,
        RED,
        BLUE,
        WATCHER
    }

    private String gameId;
    private Team team = Team.RANDOM;

    public JoinGameRequest(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(getGameId());
        s.append(", team=").append(getTeam());
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.gameId = StringUtil.readUTF8String(messageData);
        this.team = Team.values()[messageData.get()];
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameId, messageData);
        messageData.put((byte)team.ordinal());
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (team == null) throw new IllegalArgumentException("Team cannot be null. Use " + Team.RANDOM);
        this.team = team;
    }
}
