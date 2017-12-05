package com.playtech.ptargame3.examplebot.scenario.steps;

import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.examplebot.SystemException;
import com.playtech.ptargame3.examplebot.logic.ContextConstants;


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
            AbstractRequest request = task.getContext().get(ContextConstants.CALLBACK_REQUEST, AbstractRequest.class);
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
            AbstractRequest request = task.getContext().get(ContextConstants.CALLBACK_REQUEST, AbstractRequest.class);
            CallbackHandler.ResponseStatus status = getLogicResources().getCallbackHandler().getResponseStatus(task, request);
            AbstractResponse response;
            if (status == CallbackHandler.ResponseStatus.SUCCESS) {
                response = (AbstractResponse) getLogicResources().getCallbackHandler().getResponse(task, request);
                checkResponse(status, response);
                processResponse(task, response);
            } else {
                checkResponse(status, null);
            }
        }
    }

    protected abstract AbstractRequest createRequest(Task task);

    protected abstract void processResponse(Task task, AbstractResponse response);

    protected void checkResponse(CallbackHandler.ResponseStatus status, AbstractResponse response) {
        if (status != CallbackHandler.ResponseStatus.SUCCESS) {
            throw new SystemException("Server request failed.");
        }
        if (response.getErrorCode() != ApiConstants.ERR_OK) {
            throw new SystemException("Error from server: " + response.getErrorCode());
        }
    }

}
