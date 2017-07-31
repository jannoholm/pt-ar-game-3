package server.task.lobby;

import common.task.LogicResources;
import common.task.Task;
import common.task.state.OneStepState;
import server.message.lobby.GetGamesResponse;
import server.task.AbstractLogic;


public class GetGamesLogic extends AbstractLogic {

    public GetGamesLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) throws Exception {
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
