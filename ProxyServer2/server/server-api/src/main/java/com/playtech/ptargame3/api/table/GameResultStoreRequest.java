package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

public class GameResultStoreRequest extends AbstractRequest {

    public enum WinnerTeam {
        DRAW,
        RED,
        BLUE
    }

    private WinnerTeam winnerTeam;
    private Collection<GameResultPlayerActivity> playerResults = new ArrayList<>();

    public GameResultStoreRequest(MessageHeader header) {
        super(header);
    }
    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        winnerTeam = WinnerTeam.values()[messageData.get()];
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
        messageData.putInt(playerResults.size());
        for (GameResultPlayerActivity playerInfo : playerResults) {
            playerInfo.format(messageData);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", players={");
        for (GameResultPlayerActivity playerInfo : playerResults) {
            s.append("(");
            playerInfo.toStringImpl(s);
            s.append(")");
        }
        s.append("}");
    }

    public WinnerTeam getWinnerTeam() {
        return winnerTeam;
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
