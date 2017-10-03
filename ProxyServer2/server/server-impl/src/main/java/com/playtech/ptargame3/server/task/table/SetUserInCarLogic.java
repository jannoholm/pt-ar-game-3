package com.playtech.ptargame3.server.task.table;

import com.playtech.ptargame3.api.table.SetUserInCarRequest;
import com.playtech.ptargame3.api.table.SetUserInCarResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.task.AbstractLogic;

public class SetUserInCarLogic extends AbstractLogic {
    public SetUserInCarLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        SetUserInCarRequest request = getInputRequest(task, SetUserInCarRequest.class);
        // TODO
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        SetUserInCarResponse response = getResponse(task, SetUserInCarResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        SetUserInCarResponse response = getResponse(task, SetUserInCarResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
