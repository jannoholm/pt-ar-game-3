package test.step;

import common.callback.CallbackHandler;
import common.task.LogicResources;
import common.task.Task;
import common.task.TaskState;
import common.task.state.TwoStepState;
import server.exception.SystemException;
import server.message.AbstractRequest;
import server.message.AbstractResponse;
import test.ContextConstants;


public abstract class SimpleCallbackStep extends AbstractStep {

    public SimpleCallbackStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    public boolean canExecute(Task task) {
        if (task.getCurrentState() == TwoStepState.FINAL) {
            AbstractRequest request = (AbstractRequest)task.getContext().get(ContextConstants.CALLBACK_REQUEST);
            if (request == null) {
                throw new SystemException("Request missing.");
            } else {
                CallbackHandler.ResponseStatus status = getLogicResources().getCallbackHandler().getResponseStatus(task, request);
                return status != CallbackHandler.ResponseStatus.PENDING;
            }
        }
        return true;
    }

    @Override
    public void execute(Task task) throws Exception {
        if (task.getCurrentState() == TwoStepState.MIDDLE) {
            AbstractRequest request = createRequest(task);
            getLogicResources().getCallbackHandler().sendCallback(task, request);
            task.getContext().put(ContextConstants.CALLBACK_REQUEST, request);
        } else if (task.getCurrentState() == TwoStepState.FINAL) {
            AbstractRequest request = (AbstractRequest)task.getContext().get(ContextConstants.CALLBACK_REQUEST);
            CallbackHandler.ResponseStatus status = getLogicResources().getCallbackHandler().getResponseStatus(task, request);
            AbstractResponse response = null;
            if (status == CallbackHandler.ResponseStatus.SUCCESS) {
                response = (AbstractResponse) getLogicResources().getCallbackHandler().getResponse(task, request);
            }
            checkResponse(task, status, response);
        }
    }

    protected abstract AbstractRequest createRequest(Task task);

    protected void checkResponse(Task task, CallbackHandler.ResponseStatus status, AbstractResponse response) {
        if (status != CallbackHandler.ResponseStatus.SUCCESS) {
            throw new SystemException("Server request failed.");
        }
    }

}
