package com.playtech.ptargame3.test;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;
import com.playtech.ptargame3.test.registry.GameRegistryStub;
import com.playtech.ptargame3.test.registry.TestSleepManager;


public interface TestLogicResources extends LogicResources {
    CallbackHandler getCallbackHandler();
    MessageParser getMessageParser();
    ProxyClientRegistry getClientRegistry();
    GameRegistryStub getGameRegistryStub();
    TestSleepManager getTestSleepManager();
}
