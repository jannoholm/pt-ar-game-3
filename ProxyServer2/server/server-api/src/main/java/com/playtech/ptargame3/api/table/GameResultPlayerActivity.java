package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.lobby.Team;

import java.nio.ByteBuffer;

public class GameResultPlayerActivity {

    private Team team;
    private byte positionInTeam;
    private int goals;
    private int bulletHits;
    private int ballTouches;
    private int boostTouches;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // message data
        team = Team.values()[messageData.get()];
        positionInTeam = messageData.get();
        goals = messageData.getInt();
        bulletHits = messageData.getInt();
        ballTouches = messageData.getInt();
        boostTouches = messageData.getInt();
    }

    public void format(ByteBuffer messageData) {
        // remember old positionInTeam
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        // data
        messageData.put((byte)team.ordinal());
        messageData.put(positionInTeam);
        messageData.putInt(goals);
        messageData.putInt(bulletHits);
        messageData.putInt(ballTouches);
        messageData.putInt(boostTouches);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("team=").append(getTeam());
        s.append(", pos=").append(getPositionInTeam());
        s.append(", results=(").append(getGoals());
        s.append(", ").append(getBulletHits());
        s.append(", ").append(getBallTouches());
        s.append(", ").append(getBoostTouches());
        s.append(")");
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

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getBulletHits() {
        return bulletHits;
    }

    public void setBulletHits(int bulletHits) {
        this.bulletHits = bulletHits;
    }

    public int getBallTouches() {
        return ballTouches;
    }

    public void setBallTouches(int ballTouches) {
        this.ballTouches = ballTouches;
    }

    public int getBoostTouches() {
        return boostTouches;
    }

    public void setBoostTouches(int boostTouches) {
        this.boostTouches = boostTouches;
    }
}
