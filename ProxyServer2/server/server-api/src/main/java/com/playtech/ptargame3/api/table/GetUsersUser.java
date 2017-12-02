package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GetUsersUser {

    private int id;
    private String name;
    private int eloRating;
    private int leaderboardPosition;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // read structure data
        id=messageData.getInt();
        name=StringUtil.readUTF8String(messageData);
        eloRating=messageData.getInt();
        leaderboardPosition=messageData.getInt();
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        messageData.putInt(id);
        StringUtil.writeUTF8String(name, messageData);
        messageData.putInt(eloRating);
        messageData.putInt(leaderboardPosition);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("id=").append(getId());
        s.append(", name=").append(getName());
        s.append(", elo=").append(getEloRating());
        s.append(", pos=").append(getLeaderboardPosition());
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

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public int getLeaderboardPosition() {
        return leaderboardPosition;
    }

    public void setLeaderboardPosition(int leaderboardPosition) {
        this.leaderboardPosition = leaderboardPosition;
    }
}
