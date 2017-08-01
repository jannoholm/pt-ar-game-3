package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.test.ContextConstants;

public abstract class AbstractStep extends AbstractLogic {

    public AbstractStep(LogicResources logicResources) {
        super(logicResources);
    }

    protected <T extends Message> T createMessage(Task task, Class<T> messageClass) {
        String clientId = (String)task.getContext().get(ContextConstants.CLIENT_ID);
        T message = getLogicResources().getMessageParser().createMessage(messageClass);
        message.getHeader().setClientId(clientId);
        return message;
    }
}
