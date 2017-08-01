package com.playtech.ptargame3.test.step;


import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.lobby.GetGamesRequest;

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
        request.setFilter("com/playtech/ptargame3/test");
        return request;
    }

}
