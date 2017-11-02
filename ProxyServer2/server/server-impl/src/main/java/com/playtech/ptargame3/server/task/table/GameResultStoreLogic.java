package com.playtech.ptargame3.server.task.table;

import com.playtech.ptargame3.api.table.GameResultStoreRequest;
import com.playtech.ptargame3.api.table.GameResultStoreResponse;
import com.playtech.ptargame3.api.table.SetUserInCarRequest;
import com.playtech.ptargame3.api.table.SetUserInCarResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.task.AbstractLogic;

public class GameResultStoreLogic extends AbstractLogic {
    public GameResultStoreLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GameResultStoreRequest request = getInputRequest(task, GameResultStoreRequest.class);
        // TODO
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
