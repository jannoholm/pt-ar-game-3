package com.playtech.ptargame3.test.step;


import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.lobby.GetGamesResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.lobby.GetGamesRequest;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.step.common.SimpleCallbackStep;

public class ValidateHostedGameStep extends SimpleCallbackStep {

    public ValidateHostedGameStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    protected AbstractRequest createRequest(Task task) {
        GetGamesRequest request = createMessage(task, GetGamesRequest.class);
        String clientName = task.getContext().get(ContextConstants.CLIENT_NAME, String.class);
        request.setAll(true);
        request.setFilter(clientName);
        return request;
    }

    @Override
    protected void processResponse(Task task, AbstractResponse response) {
        String aiType = task.getContext().get(ContextConstants.HOSTING_GAME_AI, String.class);
        int players = task.getContext().get(ContextConstants.HOSTING_GAME_PLAYERS, Integer.class);
        String gameId = task.getContext().get(ContextConstants.HOSTING_GAME_ID, String.class);
        GetGamesResponse getGamesResponse = (GetGamesResponse)response;
        if (getGamesResponse.getGames().size() != 1) {
            throw new SystemException("Unexpected result count for getgames: " + getGamesResponse.getGames().size());
        }
        if (!getGamesResponse.getGames().get(0).getGameId().equals(gameId)) {
            throw new SystemException("Invalid gameId. Expected: " + gameId + ", actual: " + getGamesResponse.getGames().get(0).getGameId());
        }
        if (!getGamesResponse.getGames().get(0).getAiType().equals(aiType)) {
            throw new SystemException("Invalid AI type. Expected: " + aiType + ", actual: " + getGamesResponse.getGames().get(0).getAiType());
        }
        if (getGamesResponse.getGames().get(0).getTotalPlaces() != players) {
            throw new SystemException("Invalid player count. Expected: " + players + ", actual: " + getGamesResponse.getGames().get(0).getTotalPlaces());
        }
    }

}
