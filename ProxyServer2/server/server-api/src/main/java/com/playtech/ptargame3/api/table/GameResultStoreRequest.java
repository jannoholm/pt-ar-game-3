package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

public class GameResultStoreRequest extends AbstractRequest {

    public enum WinnerTeam {
        DRAW,
        RED,
        BLUE
    }

    private String gameId;
    private WinnerTeam winnerTeam;
    private boolean suddenDeath;
    private int gameTime;
    private Collection<GameResultPlayerActivity> playerResults = new ArrayList<>();

    public GameResultStoreRequest(MessageHeader header) {
        super(header);
    }
    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        gameId = StringUtil.readUTF8String(messageData);
        winnerTeam = WinnerTeam.values()[messageData.get()];
        suddenDeath = messageData.get() > 0;
        gameTime = messageData.getInt();
        int size = messageData.getInt();
        for (int i = 0; i < size; ++i) {
            GameResultPlayerActivity playerInfo = new GameResultPlayerActivity();
            playerInfo.parse(messageData);
            playerResults.add(playerInfo);
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameId, messageData);
        messageData.put((byte)winnerTeam.ordinal());
        messageData.put((byte)(suddenDeath ? 1 : 0));
        messageData.putInt(gameTime);
        messageData.putInt(playerResults.size());
        for (GameResultPlayerActivity playerInfo : playerResults) {
            playerInfo.format(messageData);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(gameId);
        s.append(", winnerTeam=").append(winnerTeam);
        s.append(", suddenDeath=").append(suddenDeath);
        s.append(", gameTime=").append(gameTime);
        s.append(", players={");
        for (GameResultPlayerActivity playerInfo : playerResults) {
            s.append("(");
            playerInfo.toStringImpl(s);
            s.append(")");
        }
        s.append("}");
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public WinnerTeam getWinnerTeam() {
        return winnerTeam;
    }

    public boolean isSuddenDeath() {
        return suddenDeath;
    }

    public void setSuddenDeath(boolean suddenDeath) {
        this.suddenDeath = suddenDeath;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public void setWinnerTeam(WinnerTeam winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public Collection<GameResultPlayerActivity> getPlayerResults() {
        return playerResults;
    }

    public void addPlayerResult(GameResultPlayerActivity playerResult) {
        this.playerResults.add( playerResult );
    }
}
