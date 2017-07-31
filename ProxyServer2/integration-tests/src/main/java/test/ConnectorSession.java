package test;

import common.callback.CallbackHandler;
import common.io.Connection;
import common.message.MessageParser;
import common.task.TaskFactory;
import server.ProxyClientRegistry;
import server.session.ClientSession;


public class ConnectorSession extends ClientSession {
    public ConnectorSession(Connection connection, MessageParser parser, CallbackHandler callbackHandler, ProxyClientRegistry clientRegistry, TaskFactory taskFactory) {
        super(connection, parser, callbackHandler, clientRegistry, taskFactory);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
