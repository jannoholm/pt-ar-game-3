package com.playtech.ptargame3.api.leaderboard;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class GetLeaderboardResponse extends AbstractResponse {

    private List<GetLeaderboardUser> users=new ArrayList<>();

    public GetLeaderboardResponse(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        int numberOfUsers=messageData.getInt();
        for (int i=0; i < numberOfUsers; ++i) {
            GetLeaderboardUser user = new GetLeaderboardUser();
            user.parse(messageData);
            users.add(user);
        }
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(users.size());
        for (GetLeaderboardUser user : users) {
            user.format(messageData);
        }
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", users={");
        for (int i = 0; i < users.size(); ++i) {
            GetLeaderboardUser user = users.get(i);
            if (i > 0) s.append(",");
            user.toStringImpl(s);
        }
        s.append("}");
    }

    public List<GetLeaderboardUser> getUsers() {
        return users;
    }

    public void addUser(GetLeaderboardUser user) {
        this.users.add(user);
    }
}
