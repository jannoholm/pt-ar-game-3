package server.message;

import common.message.Message;
import common.message.MessageFactory;
import common.message.MessageHeader;
import server.message.game.GameContolMessage;
import server.message.game.GameUpdateMessage;
import server.message.general.PingRequest;
import server.message.general.PingResponse;
import server.message.lobby.GetDetailedGameInfoRequest;
import server.message.lobby.GetDetailedGameInfoResponse;
import server.message.lobby.GetGamesRequest;
import server.message.lobby.GetGamesResponse;
import server.message.game.HostGameRequest;
import server.message.game.HostGameResponse;
import server.message.game.JoinGameRequest;
import server.message.game.JoinGameResponse;
import server.message.general.JoinServerRequest;
import server.message.general.JoinServerResponse;

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
