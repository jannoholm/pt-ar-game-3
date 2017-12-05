package com.playtech.ptargame3.examplebot.scenario.steps;

import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.examplebot.logic.AbstractBotLogic;
import com.playtech.ptargame3.examplebot.logic.ContextConstants;

public abstract class AbstractStep extends AbstractBotLogic {

    public AbstractStep(LogicResources logicResources) {
        super(logicResources);
    }

    protected <T extends Message> T createMessage(Task task, Class<T> messageClass) {
        String clientId = task.getContext().get(ContextConstants.CLIENT_ID, String.class);
        T message = getLogicResources().getMessageParser().createMessage(messageClass);
        message.getHeader().setClientId(clientId);
        return message;
    }
}
