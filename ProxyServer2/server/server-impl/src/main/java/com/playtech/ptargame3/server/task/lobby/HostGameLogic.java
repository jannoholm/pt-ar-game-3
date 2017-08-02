package com.playtech.ptargame3.server.task.lobby;


import com.playtech.ptargame3.api.lobby.HostGameRequest;
import com.playtech.ptargame3.api.lobby.HostGameResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.util.concurrent.atomic.AtomicInteger;

public class HostGameLogic extends AbstractLogic {
    public static final AtomicInteger count = new AtomicInteger();

    public HostGameLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        HostGameRequest request = getInputRequest(task, HostGameRequest.class);
        String clientId = request.getHeader().getClientId();
        String name = getLogicResources().getClientRegistry().getName(clientId);
        String gameId = getLogicResources().getGameRegistry().createGame(
                clientId,
                name + " " + count.incrementAndGet(),
                request.getPlayers(),
                request.isJoinAsPlayer(),
                request.getAiType()
        );
        HostGameResponse response = getResponse(task, HostGameResponse.class);
        response.setGameId(gameId);
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        HostGameResponse response = task.getContext().get(ContextConstants.RESPONSE, HostGameResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        HostGameResponse response = getResponse(task, HostGameResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

}
