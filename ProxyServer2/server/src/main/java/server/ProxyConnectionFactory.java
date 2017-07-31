package server;

import common.callback.CallbackHandler;
import common.io.ConnectionFactory;
import common.io.ConnectionHandler;
import common.io.separator.Decoder;
import common.io.separator.Encoder;
import common.io.separator.LengthDecoder;
import common.io.separator.LengthEncoder;
import common.message.MessageParser;
import common.task.TaskFactory;
import server.session.ClientSession;

public class ProxyConnectionFactory implements ConnectionFactory {

    private static final int MESSAGE_LIMIT = 16384;

    private final MessageParser messageParser;
    private final CallbackHandler callbackHandler;
    private final ProxyClientRegistry clientRegistry;
    private final TaskFactory taskFactory;

    public ProxyConnectionFactory(MessageParser messageParser, CallbackHandler callbackHandler, ProxyClientRegistry clientRegistry, TaskFactory taskFactory) {
        this.messageParser = messageParser;
        this.callbackHandler = callbackHandler;
        this.clientRegistry = clientRegistry;
        this.taskFactory = taskFactory;
    }

    @Override
    public ConnectionHandler createConnection() {
        Encoder encoder = new LengthEncoder(MESSAGE_LIMIT);
        Decoder decoder = new LengthDecoder(MESSAGE_LIMIT);
        ConnectionHandler handler = new ConnectionHandler(encoder, decoder);
        ClientSession session = new ClientSession(handler, this.messageParser, this.callbackHandler, clientRegistry, taskFactory);
        handler.setSession(session);
        return handler;
    }

}
