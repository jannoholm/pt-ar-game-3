package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.api.lobby.JoinGameRequest;
import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.exception.FlowStopExcepton;
import com.playtech.ptargame3.test.step.common.SimpleCallbackStep;

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
        String gameId = task.getContext().get(ContextConstants.JOIN_GAME_ID, String.class);
        task.getContext().put(ContextConstants.PLAY_GAME_ID, gameId);
    }

    protected void checkResponse(CallbackHandler.ResponseStatus status, AbstractResponse response) {
        if (status != CallbackHandler.ResponseStatus.SUCCESS) {
            throw new SystemException("Server request failed.");
        }
        if (response.getErrorCode() == ApiConstants.ERR_GAME_FULL) {
            // expected error.
            throw new FlowStopExcepton("Stopping scenario normally. No games to join.");
        }
        if (response.getErrorCode() != ApiConstants.ERR_OK) {
            throw new SystemException("Error from server: " + response.getErrorCode());
        }
    }
}
