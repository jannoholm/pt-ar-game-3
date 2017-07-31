package server;


import common.callback.CallbackHandler;
import common.message.MessageParser;

public class LogicResourcesImpl implements ProxyLogicResources {

    private final CallbackHandler callbackHandler;
    private final MessageParser messageParser;
    private final ProxyClientRegistry clientRegistry;

    public LogicResourcesImpl(CallbackHandler callbackHandler, MessageParser messageParser, ProxyClientRegistry clientRegistry) {
        this.callbackHandler = callbackHandler;
        this.messageParser = messageParser;
        this.clientRegistry = clientRegistry;
    }

    @Override
    public CallbackHandler getCallbackHandler() {
        return this.callbackHandler;
    }

    @Override
    public MessageParser getMessageParser() {
        return this.messageParser;
    }

    @Override
    public ProxyClientRegistry getClientRegistry() {
        return this.clientRegistry;
    }
}
