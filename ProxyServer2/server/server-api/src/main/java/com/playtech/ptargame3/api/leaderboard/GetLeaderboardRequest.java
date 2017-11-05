package com.playtech.ptargame3.api.leaderboard;


import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetLeaderboardRequest extends AbstractRequest {

    private List<Integer> userIds = new ArrayList<>();

    public GetLeaderboardRequest(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        int numberOfUsers=messageData.getInt();
        for (int i=0; i < numberOfUsers; ++i) {
            userIds.add(messageData.getInt());
        }
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(userIds.size());
        for (Integer userId : userIds) {
            messageData.putInt(userId);
        }
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", ").append(userIds);
    }

    public Collection<Integer> getUserIds() {
        return userIds;
    }

    public void addUserId(int userId) {
        this.userIds.add(userId);
    }
}
