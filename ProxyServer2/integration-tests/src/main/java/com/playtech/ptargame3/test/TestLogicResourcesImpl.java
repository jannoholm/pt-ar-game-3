package com.playtech.ptargame3.test;


import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;
import com.playtech.ptargame3.test.registry.GameRegistryStub;
import com.playtech.ptargame3.test.registry.TestSleepManager;

public class TestLogicResourcesImpl implements TestLogicResources {

    private final CallbackHandler callbackHandler;
    private final MessageParser messageParser;
    private final ProxyClientRegistry clientRegistry;
    private final GameRegistryStub gameRegistryStub;
    private final TestSleepManager testSleepManager;

    public TestLogicResourcesImpl(CallbackHandler callbackHandler, MessageParser messageParser, ProxyClientRegistry clientRegistry, GameRegistryStub gameRegistryStub, TestSleepManager testSleepManager) {
        this.callbackHandler = callbackHandler;
        this.messageParser = messageParser;
        this.clientRegistry = clientRegistry;
        this.gameRegistryStub = gameRegistryStub;
        this.testSleepManager = testSleepManager;
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
    public GameRegistryStub getGameRegistryStub() {
        return this.gameRegistryStub;
    }

    @Override
    public TestSleepManager getTestSleepManager() {
        return testSleepManager;
    }
}
