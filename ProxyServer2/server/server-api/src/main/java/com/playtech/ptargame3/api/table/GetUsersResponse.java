package com.playtech.ptargame3.api.table;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetUsersResponse extends AbstractResponse {

    private List<GetUsersUser> users=new ArrayList<>();

    public GetUsersResponse(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        int numberOfUsers=messageData.getInt();
        for (int i=0; i < numberOfUsers; ++i) {
            GetUsersUser user = new GetUsersUser();
            user.parse(messageData);
            users.add(user);
        }
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(users.size());
        for (GetUsersUser user : users) {
            user.format(messageData);
        }
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", users={");
        for (int i = 0; i < users.size(); ++i) {
            GetUsersUser user = users.get(i);
            if (i > 0) s.append(",");
            user.toStringImpl(s);
        }
        s.append("}");
    }

    public List<GetUsersUser> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(GetUsersUser user) {
        this.users.add(user);
    }

}
