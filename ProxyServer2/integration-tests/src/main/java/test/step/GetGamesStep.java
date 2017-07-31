package test.step;


import common.task.LogicResources;
import common.task.Task;
import common.task.TaskState;
import common.task.state.TwoStepState;
import server.message.AbstractRequest;
import server.message.lobby.GetGamesRequest;

public class GetGamesStep extends SimpleCallbackStep {

    public GetGamesStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    protected AbstractRequest createRequest(Task task) {
        GetGamesRequest request = createMessage(task, GetGamesRequest.class);
        request.setAll(false);
        request.setFilter("test");
        return request;
    }

}
