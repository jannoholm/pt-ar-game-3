package com.playtech.ptargame3.examplebot.logic;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.callback.ClientRegistry;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.examplebot.scenario.SleepManager;

public class BotLogicResourcesImpl implements BotLogicResources {
    private final CallbackHandler callbackHandler;
    private final MessageParser messageParser;
    private final ClientRegistry clientRegistry;
    private final SleepManager sleepManager;

    public BotLogicResourcesImpl(CallbackHandler callbackHandler, MessageParser messageParser, ClientRegistry clientRegistry, SleepManager sleepManager) {
        this.callbackHandler = callbackHandler;
        this.messageParser = messageParser;
        this.clientRegistry = clientRegistry;
        this.sleepManager = sleepManager;
    }

    public CallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    public MessageParser getMessageParser() {
        return messageParser;
    }

    public ClientRegistry getClientRegistry() {
        return clientRegistry;
    }

    @Override
    public SleepManager getSleepManager() {
        return sleepManager;
    }
}
