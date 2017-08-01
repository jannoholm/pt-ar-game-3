package com.playtech.ptargame3.server.task;


import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.server.ProxyLogicResources;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.ApiConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractLogic implements Logic {

    private static final Logger logger = Logger.getLogger(AbstractLogic.class.getName());

    private final ProxyLogicResources logicResources;

    public AbstractLogic(LogicResources logicResources) {
        this.logicResources = (ProxyLogicResources)logicResources;
    }

    @Override
    public TaskState initialState() {
        return OneStepState.INITIAL;
    }

    @Override
    public final ProxyLogicResources getLogicResources() {
        return this.logicResources;
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task context) {
        return Collections.emptyList();
    }

    @Override
    public boolean canExecute(Task context) {
        return true;
    }

    @Override
    public void finishSuccess(Task context) {
    }

    @Override
    public void finishError(Task context, Exception e) {
        logger.log(Level.INFO, "Task finished with error", e);
    }

    protected <T extends AbstractResponse> T getResponse(Task context, Class<T> responseClass) {
        MessageTaskInput input = (MessageTaskInput)context.getContext().getInput();
        T response = getLogicResources().getMessageParser().createResponse(input.getMessage(), responseClass);
        response.getHeader().setClientId(input.getMessage().getHeader().getClientId());
        return response;
    }

    protected <T extends AbstractResponse> T getResponse(Task context, Class<T> responseClass, Exception e) {
        T response = getResponse(context, responseClass);
        response.setErrorCode(ApiConstants.ERR_SYSTEM);
        response.setErrorMessage(e.getMessage());
        return response;
    }

}
