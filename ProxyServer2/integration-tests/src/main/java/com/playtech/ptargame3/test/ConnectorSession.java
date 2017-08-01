package com.playtech.ptargame3.test;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.io.Connection;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.server.ProxyClientRegistry;
import com.playtech.ptargame3.server.session.ClientSession;


public class ConnectorSession extends ClientSession {
    public ConnectorSession(Connection connection, MessageParser parser, CallbackHandler callbackHandler, ProxyClientRegistry clientRegistry, TaskFactory taskFactory) {
        super(connection, parser, callbackHandler, clientRegistry, taskFactory);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
