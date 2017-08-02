package com.playtech.ptargame3.api.lobby;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.common.message.MessageHeader;

public class JoinGameResponse extends AbstractResponse {

    private Team team;

    public JoinGameResponse(MessageHeader header) {
        super(header);
    }

}
