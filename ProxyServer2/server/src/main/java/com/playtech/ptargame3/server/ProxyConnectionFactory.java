package com.playtech.ptargame3.server;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.io.ConnectionFactory;
import com.playtech.ptargame3.common.io.ConnectionHandler;
import com.playtech.ptargame3.common.io.separator.Decoder;
import com.playtech.ptargame3.common.io.separator.Encoder;
import com.playtech.ptargame3.common.io.separator.LengthDecoder;
import com.playtech.ptargame3.common.io.separator.LengthEncoder;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.server.session.ClientSession;

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
