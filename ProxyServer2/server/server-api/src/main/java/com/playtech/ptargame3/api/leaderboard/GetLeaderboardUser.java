package com.playtech.ptargame3.api.leaderboard;


import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GetLeaderboardUser {

    private int userId;
    private String name;
    private int eloRating;
    private int matches;
    private int goals;
    private int bulletHits;
    private int totalScore;
    private int ballTouches;
    private int boostTouches;
    private int position;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // read structure data
        userId=messageData.getInt();
        name=StringUtil.readUTF8String(messageData);
        eloRating=messageData.getInt();
        matches=messageData.getInt();
        goals=messageData.getInt();
        bulletHits=messageData.getInt();
        totalScore=messageData.getInt();
        ballTouches=messageData.getInt();
        boostTouches=messageData.getInt();
        position=messageData.getInt();
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        messageData.putInt(userId);
        StringUtil.writeUTF8String(name, messageData);
        messageData.putInt(eloRating);
        messageData.putInt(matches);
        messageData.putInt(goals);
        messageData.putInt(bulletHits);
        messageData.putInt(totalScore);
        messageData.putInt(ballTouches);
        messageData.putInt(boostTouches);
        messageData.putInt(position);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("id=").append(getUserId());
        s.append(", name=").append(getName());
        s.append(", eloRating=").append(getEloRating());
        s.append(", matches=").append(getMatches());
        s.append(", goals=").append(getGoals());
        s.append(", bulletHits=").append(getBulletHits());
        s.append(", totalScore=").append(getTotalScore());
        s.append(", ballTouches=").append(getBallTouches());
        s.append(", boostTouches=").append(getBoostTouches());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
