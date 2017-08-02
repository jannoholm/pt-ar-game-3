package com.playtech.ptargame3.server;


import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.server.registry.GameRegistry;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;

public class LogicResourcesImpl implements ProxyLogicResources {

    private final CallbackHandler callbackHandler;
    private final MessageParser messageParser;
    private final ProxyClientRegistry clientRegistry;
    private final GameRegistry gameRegistry;
    private final TaskFactory taskFactory;

    public LogicResourcesImpl(CallbackHandler callbackHandler, MessageParser messageParser, ProxyClientRegistry clientRegistry, GameRegistry gameRegistry, TaskFactory taskFactory) {
        this.callbackHandler = callbackHandler;
        this.messageParser = messageParser;
        this.clientRegistry = clientRegistry;
        this.gameRegistry = gameRegistry;
        this.taskFactory = taskFactory;
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

    @Override
    public GameRegistry getGameRegistry() {
        return gameRegistry;
    }

    @Override
    public TaskFactory getTaskFactory() {
        return taskFactory;
    }
}
