package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.api.lobby.JoinGameRequest;
import com.playtech.ptargame3.api.lobby.JoinGameResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskInput;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.task.GameUpdateTaskInput;
import com.playtech.ptargame3.server.task.game.PushGameLobbyUpdateLogic;


public class JoinGameLogic extends AbstractLogic {
    public JoinGameLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        JoinGameRequest request = getInputRequest(task, JoinGameRequest.class);
        switch (request.getTeam()) {
            case WATCHER:
                getLogicResources().getGameRegistry().joinWatcher(request.getGameId(), request.getHeader().getClientId());
            case RED:
                getLogicResources().getGameRegistry().joinPlayer(request.getGameId(), request.getHeader().getClientId(), GameRegistryGame.Team.RED);
            case BLUE:
                getLogicResources().getGameRegistry().joinPlayer(request.getGameId(), request.getHeader().getClientId(), GameRegistryGame.Team.BLUE);
            default:
                getLogicResources().getGameRegistry().joinPlayer(request.getGameId(), request.getHeader().getClientId());
        }
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        JoinGameResponse response = getResponse(task, JoinGameResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);

        // push update
        JoinGameRequest request = getInputRequest(task, JoinGameRequest.class);
        TaskInput pushInput = new GameUpdateTaskInput(PushGameLobbyUpdateLogic.TASK_TYPE, request.getGameId(), request.getHeader().getClientId());
        Task pushTask = getLogicResources().getTaskFactory().getTask(pushInput);
        pushTask.scheduleExecution();
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        JoinGameResponse response = getResponse(task, JoinGameResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
