package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;


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
    public void execute(Task task) {
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
