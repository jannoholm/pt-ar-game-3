package server;

import common.callback.CallbackHandler;
import common.message.MessageParser;
import common.task.LogicResources;

public interface ProxyLogicResources extends LogicResources {
    CallbackHandler getCallbackHandler();
    MessageParser getMessageParser();
    ProxyClientRegistry getClientRegistry();
}
