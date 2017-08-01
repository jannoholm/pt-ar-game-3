package com.playtech.ptargame3.api.lobby;

import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.common.util.StringUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class GetDetailedGameInfoResponse extends AbstractResponse {
    private String gameName;
    private int totalPlaces;
    private int freePlaces;
    private String aiType;
    private List<GetDetailedGameInfoRider> riders = new ArrayList<>();

    public GetDetailedGameInfoResponse(MessageHeader header) {
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
            GetDetailedGameInfoRider rider = new GetDetailedGameInfoRider();
            rider.parse(messageData);
            riders.add(rider);
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameName, messageData);
        messageData.putInt(totalPlaces);
        messageData.putInt(freePlaces);
        StringUtil.writeUTF8String(gameName, messageData);
        messageData.putInt(riders.size());
        for (GetDetailedGameInfoRider rider : riders) {
            rider.format(messageData);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append("riders={");
        for (GetDetailedGameInfoRider rider : riders) {
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

    public Collection<GetDetailedGameInfoRider> getRiders() {
        return Collections.unmodifiableCollection(riders);
    }

    public void addRider(GetDetailedGameInfoRider rider) {
        this.riders.add(rider);
    }
}
