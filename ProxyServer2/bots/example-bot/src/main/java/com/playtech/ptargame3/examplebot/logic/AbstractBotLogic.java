package com.playtech.ptargame3.examplebot.logic;


import com.playtech.ptargame3.api.AbstractMessage;
import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.OneStepState;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBotLogic implements Logic {
    private static final Logger logger = Logger.getLogger(AbstractBotLogic.class.getName());

    private final BotLogicResources logicResources;

    public AbstractBotLogic(LogicResources logicResources) {
        this.logicResources = (BotLogicResources)logicResources;
    }

    @Override
    public TaskState initialState() {
        return OneStepState.INITIAL;
    }

    @Override
    public final BotLogicResources getLogicResources() {
        return this.logicResources;
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task task) {
        return Collections.emptyList();
    }

    @Override
    public boolean canExecute(Task task) {
        return true;
    }

    @Override
    public void finishSuccess(Task task) {
    }

    @Override
    public void finishError(Task task, Exception e) {
        logger.log(Level.INFO, "Task finished with error", e);
    }

    @SuppressWarnings("unchecked")
    protected <T extends AbstractMessage> T getInputMessage(Task task, Class<T> requestClass) {
        MessageTaskInput input = (MessageTaskInput)task.getContext().getInput();
        return (T)input.getMessage();
    }

    @SuppressWarnings("unchecked")
    protected <T extends AbstractRequest> T getInputRequest(Task task, Class<T> requestClass) {
        MessageTaskInput input = (MessageTaskInput)task.getContext().getInput();
        return (T)input.getMessage();
    }

    protected <T extends AbstractResponse> T getResponse(Task task, Class<T> responseClass) {
        MessageTaskInput input = (MessageTaskInput)task.getContext().getInput();
        T response = getLogicResources().getMessageParser().createResponse(input.getMessage(), responseClass);
        response.getHeader().setClientId(input.getMessage().getHeader().getClientId());
        return response;
    }

    protected <T extends AbstractResponse> T getResponse(Task task, Class<T> responseClass, Exception e) {
        T response = getResponse(task, responseClass);
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException)e;
            response.setErrorCode(apiException.getErrorCode());
        } else {
            response.setErrorCode(ApiConstants.ERR_SYSTEM);
        }
        response.setErrorMessage(e.getMessage());
        return response;
    }
}
