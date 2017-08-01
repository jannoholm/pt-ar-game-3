package com.playtech.ptargame3.server.message;

import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.message.MessageFactory;
import com.playtech.ptargame3.common.message.MessageHeader;
import com.playtech.ptargame3.server.message.game.GameContolMessage;
import com.playtech.ptargame3.server.message.game.GameUpdateMessage;
import com.playtech.ptargame3.server.message.general.PingRequest;
import com.playtech.ptargame3.server.message.general.PingResponse;
import com.playtech.ptargame3.server.message.lobby.GetDetailedGameInfoRequest;
import com.playtech.ptargame3.server.message.lobby.GetDetailedGameInfoResponse;
import com.playtech.ptargame3.server.message.lobby.GetGamesRequest;
import com.playtech.ptargame3.server.message.lobby.GetGamesResponse;
import com.playtech.ptargame3.server.message.game.HostGameRequest;
import com.playtech.ptargame3.server.message.game.HostGameResponse;
import com.playtech.ptargame3.server.message.game.JoinGameRequest;
import com.playtech.ptargame3.server.message.game.JoinGameResponse;
import com.playtech.ptargame3.server.message.general.JoinServerRequest;
import com.playtech.ptargame3.server.message.general.JoinServerResponse;

import java.util.HashMap;
import java.util.Map;

public class ProxyMessageFactory implements MessageFactory {

    private final Map<Integer, Class<? extends Message>> typeToClass = new HashMap<>();
    private final Map<Class<? extends Message>, Integer> classToType = new HashMap<>();

    public void initialize() {
        // general
        addMessage(1000, PingRequest.class);
        addMessage(1001, PingResponse.class);
        addMessage(1002, JoinServerRequest.class);
        addMessage(1003, JoinServerResponse.class);

        // lobby
        addMessage(2000, GetGamesRequest.class);
        addMessage(2001, GetGamesResponse.class);
        addMessage(2004, GetDetailedGameInfoRequest.class);
        addMessage(2005, GetDetailedGameInfoResponse.class);

        // game-play
        addMessage(3000, JoinGameRequest.class);
        addMessage(3001, JoinGameResponse.class);
        addMessage(3002, HostGameRequest.class);
        addMessage(3003, HostGameResponse.class);
        addMessage(3004, GameContolMessage.class);
        addMessage(3006, GameUpdateMessage.class);
    }

    private void addMessage(int messageType, Class<? extends Message> messageClass) {
        typeToClass.put(messageType, messageClass);
        classToType.put(messageClass, messageType);
    }

    @Override
    public Message createMessage(MessageHeader header) {
        Class<? extends Message> messageClass = typeToClass.get(header.getMessageType());
        if (messageClass == null) {
            throw new IllegalArgumentException("Unknown message type: " + header.getMessageType());
        }
        try {
            return messageClass.getConstructor(MessageHeader.class).newInstance(header);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid message class: " + messageClass.getName(), e);
        }
    }

    @Override
    public <T extends Message> int getMessageType(Class<T> messageClass) {
        Integer messageType = classToType.get(messageClass);
        if (messageType == null) {
            throw new IllegalArgumentException("Unknown message class: " + messageClass);
        }
        return messageType;
    }

}
