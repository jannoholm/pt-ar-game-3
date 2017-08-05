package com.playtech.ptargame3.api.lobby;

import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;

public class GetGamesGameInfo {

    private String gameId;
    private String gameName;
    private int totalPlaces;
    private int freePlaces;
    private String aiType;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes).order(messageData.order());

        // read structure data
        gameId=StringUtil.readUTF8String(messageData);
        gameName=StringUtil.readUTF8String(messageData);
        totalPlaces=messageData.getInt();
        freePlaces=messageData.getInt();
        aiType =StringUtil.readUTF8String(messageData);
    }

    public void format(ByteBuffer messageData) {
        // remember old position
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        StringUtil.writeUTF8String(gameId, messageData);
        StringUtil.writeUTF8String(gameName, messageData);
        messageData.putInt(totalPlaces);
        messageData.putInt(freePlaces);
        StringUtil.writeUTF8String(aiType, messageData);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("gameId=").append(getGameId());
        s.append(", gameName=").append(getGameName());
        s.append(", places=").append(getTotalPlaces());
        s.append(", free=").append(getFreePlaces());
        s.append(", ai=").append(getAiType());
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public String getAiType() {
        return aiType;
    }

    public void setAiType(String aiType) {
        this.aiType = aiType;
    }
}
