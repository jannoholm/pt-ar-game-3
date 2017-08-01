package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.api.lobby.GetGamesResponse;
import com.playtech.ptargame3.server.task.AbstractLogic;


public class GetGamesLogic extends AbstractLogic {

    public GetGamesLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        if (task.getCurrentState() == OneStepState.FINAL) {
            // TODO
        }
    }

    @Override
    public void finishSuccess(Task context) {
        super.finishSuccess(context);
        GetGamesResponse response = getResponse(context, GetGamesResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task context, Exception e) {
        super.finishError(context, e);
        GetGamesResponse response = getResponse(context, GetGamesResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
