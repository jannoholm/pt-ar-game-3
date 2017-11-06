package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

public class GameResultStoreResponse extends AbstractResponse {

    private Collection<GameResultPlayerScore> playerResults = new ArrayList<>();

    public GameResultStoreResponse(MessageHeader header) {
        super(header);
    }
    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        int size = messageData.getInt();
        for (int i = 0; i < size; ++i) {
            GameResultPlayerScore playerInfo = new GameResultPlayerScore();
            playerInfo.parse(messageData);
            playerResults.add(playerInfo);
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(playerResults.size());
        for (GameResultPlayerScore playerInfo : playerResults) {
            playerInfo.format(messageData);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", players={");
        for (GameResultPlayerScore playerInfo : playerResults) {
            s.append("(");
            playerInfo.toStringImpl(s);
            s.append(")");
        }
        s.append("}");
    }

    public Collection<GameResultPlayerScore> getPlayerResults() {
        return playerResults;
    }

    public void addPlayerResult(GameResultPlayerScore playerResult) {
        this.playerResults.add( playerResult );
    }
}
