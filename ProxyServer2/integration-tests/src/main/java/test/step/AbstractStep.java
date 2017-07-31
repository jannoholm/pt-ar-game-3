package test.step;

import common.message.Message;
import common.task.LogicResources;
import common.task.Task;
import server.task.AbstractLogic;
import test.ContextConstants;

public abstract class AbstractStep extends AbstractLogic {

    public AbstractStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void finishSuccess(Task context) {
        super.finishSuccess(context);
    }

    @Override
    public void finishError(Task context, Exception e) {
        super.finishError(context, e);
    }

    protected <T extends Message> T createMessage(Task task, Class<T> messageClass) {
        String clientId = (String)task.getContext().get(ContextConstants.CLIENT_ID);
        T message = getLogicResources().getMessageParser().createMessage(messageClass);
        message.getHeader().setClientId(clientId);
        return message;
    }
}
