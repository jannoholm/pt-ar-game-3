package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.lobby.JoinGameRequest;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.ContextConstants;

public class JoinGameStep extends SimpleCallbackStep {
    public JoinGameStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    protected AbstractRequest createRequest(Task task) {
        // expecting gameId from context
        String gameId = task.getContext().get(ContextConstants.JOIN_GAME_ID, String.class);

        JoinGameRequest request = createMessage(task, JoinGameRequest.class);
        request.setGameId(gameId);
        return request;
    }

    @Override
    protected void processResponse(Task task, AbstractResponse response) {
    }
}
