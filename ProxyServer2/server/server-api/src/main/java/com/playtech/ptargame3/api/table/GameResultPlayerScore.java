package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.lobby.Team;

import java.nio.ByteBuffer;

public class GameResultPlayerScore {

    private Team team;
    private byte positionInTeam;
    private int score;
    private int eloRating;
    private int leaderboardPosition;
    private int eloRatingChange;
    private int leaderboardPositionChange;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // message data
        team = Team.values()[messageData.get()];
        positionInTeam = messageData.get();
        score = messageData.getInt();
        eloRating = messageData.getInt();
        leaderboardPosition = messageData.getInt();
        eloRatingChange = messageData.getInt();
        leaderboardPositionChange = messageData.getInt();
    }

    public void format(ByteBuffer messageData) {
        // remember old positionInTeam
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        // data
        messageData.put((byte)team.ordinal());
        messageData.put(positionInTeam);
        messageData.putInt(score);
        messageData.putInt(eloRating);
        messageData.putInt(leaderboardPosition);
        messageData.putInt(eloRatingChange);
        messageData.putInt(leaderboardPositionChange);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("team=").append(getTeam());
        s.append(", tp=").append(getPositionInTeam());
        s.append(", score=").append(getScore());
        s.append(", elo=").append(getEloRating());
        s.append(", pos=").append(getLeaderboardPosition());
        s.append(", eloDiff=").append(getEloRatingChange());
        s.append(", posDiff=").append(getLeaderboardPositionChange());
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public int getEloRatingChange() {
        return eloRatingChange;
    }

    public void setEloRatingChange(int eloRatingChange) {
        this.eloRatingChange = eloRatingChange;
    }

    public int getLeaderboardPositionChange() {
        return leaderboardPositionChange;
    }

    public void setLeaderboardPositionChange(int leaderboardPositionChange) {
        this.leaderboardPositionChange = leaderboardPositionChange;
    }
}
