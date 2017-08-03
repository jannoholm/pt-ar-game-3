package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.api.AbstractRequest;
import com.playtech.ptargame3.api.AbstractResponse;
import com.playtech.ptargame3.api.lobby.HostGameRequest;
import com.playtech.ptargame3.api.lobby.HostGameResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.step.common.SimpleCallbackStep;

public class HostGameStep extends SimpleCallbackStep {
    public HostGameStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    protected AbstractRequest createRequest(Task task) {
        HostGameRequest request = createMessage(task, HostGameRequest.class);

        // generate data
        int players = ((int)(Math.random()*2)+2)*2;
        String aiType = "myAI" + (int)(Math.random()*10);

        // store in task
        task.getContext().put(ContextConstants.HOSTING_GAME_AI, aiType);
        task.getContext().put(ContextConstants.HOSTING_GAME_PLAYERS, players);

        // set in request
        request.setPlayers(players);
        request.setAiType(aiType);
        request.setJoinAsPlayer(true);
        return request;
    }

    @Override
    protected void processResponse(Task task, AbstractResponse response) {
        HostGameResponse hostGameResponse = (HostGameResponse)response;
        task.getContext().put(ContextConstants.HOSTING_GAME_ID, hostGameResponse.getGameId());
        task.getContext().put(ContextConstants.PLAY_GAME_ID, hostGameResponse.getGameId());
        if (StringUtil.isNull(hostGameResponse.getGameId())) throw new SystemException("Invalid response from server. gameid missing.");
    }
}
