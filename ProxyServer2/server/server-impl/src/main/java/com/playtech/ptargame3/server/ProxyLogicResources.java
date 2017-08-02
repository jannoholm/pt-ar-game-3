package com.playtech.ptargame3.server;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.server.registry.GameRegistry;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;

public interface ProxyLogicResources extends LogicResources {
    CallbackHandler getCallbackHandler();
    MessageParser getMessageParser();
    ProxyClientRegistry getClientRegistry();
    GameRegistry getGameRegistry();
    TaskFactory getTaskFactory();
}
