package com.playtech.ptargame3.test;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.io.Connection;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.server.registry.GameRegistry;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;
import com.playtech.ptargame3.server.session.ClientSession;


public class ConnectorSession extends ClientSession {
    public ConnectorSession(Connection connection, MessageParser parser, CallbackHandler callbackHandler, ProxyClientRegistry clientRegistry, GameRegistry gameRegistry, TaskFactory taskFactory) {
        super(connection, parser, callbackHandler, clientRegistry, gameRegistry, taskFactory);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
