package com.playtech.ptargame3.examplebot.logic;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.callback.ClientRegistry;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.examplebot.scenario.SleepManager;

public interface BotLogicResources extends LogicResources {
    CallbackHandler getCallbackHandler();

    MessageParser getMessageParser();

    ClientRegistry getClientRegistry();

    SleepManager getSleepManager();
}
