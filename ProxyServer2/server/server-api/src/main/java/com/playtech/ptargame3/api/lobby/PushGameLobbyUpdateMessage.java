package com.playtech.ptargame3.api.lobby;

import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class PushGameLobbyUpdateMessage extends AbstractMessage {

    private String gameName;
    private int totalPlaces;
    private int freePlaces;
    private String aiType;
    private Collection<PendingGamePlayer> players = new ArrayList<>();

    public PushGameLobbyUpdateMessage(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        gameName = StringUtil.readUTF8String(messageData);
        totalPlaces = messageData.getInt();
        freePlaces = messageData.getInt();
        gameName = StringUtil.readUTF8String(messageData);
        int size = messageData.getInt();
        for (int i = 0; i < size; ++i) {
            PendingGamePlayer rider = new PendingGamePlayer();
            rider.parse(messageData);
            players.add(rider);
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameName, messageData);
        messageData.putInt(totalPlaces);
        messageData.putInt(freePlaces);
        StringUtil.writeUTF8String(gameName, messageData);
        messageData.putInt(players.size());
        for (PendingGamePlayer rider : players) {
            rider.format(messageData);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", players={");
        for (PendingGamePlayer rider : players) {
            s.append("(");
            rider.toStringImpl(s);
            s.append(")");
        }
        s.append("}");
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

    public Collection<PendingGamePlayer> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    public void addPlayer(PendingGamePlayer player) {
        this.players.add(player);
    }
}
