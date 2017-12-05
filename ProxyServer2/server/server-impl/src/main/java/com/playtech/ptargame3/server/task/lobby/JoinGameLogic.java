package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.api.lobby.JoinGameRequest;
import com.playtech.ptargame3.api.lobby.JoinGameResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskInput;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.registry.GameRegistryGamePlayer;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.task.GameUpdateTaskInput;
import com.playtech.ptargame3.server.task.game.PushGameLobbyUpdateLogic;
import com.playtech.ptargame3.server.util.TeamConverter;


public class JoinGameLogic extends AbstractLogic {
    public JoinGameLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        JoinGameRequest request = getInputRequest(task, JoinGameRequest.class);
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(request.getGameId());

        switch (request.getTeam()) {
            case WATCHER:
                game.addWatcher(request.getHeader().getClientId());
            case RED:
                game.addPlayer(request.getHeader().getClientId(), GameRegistryGame.Team.RED, -1);
            case BLUE:
                game.addPlayer(request.getHeader().getClientId(), GameRegistryGame.Team.BLUE, -1);
            default:
                game.addPlayer(request.getHeader().getClientId(), null, -1);
        }
        JoinGameResponse response = getResponse(task, JoinGameResponse.class);

        for (GameRegistryGamePlayer player : game.getPlayers()) {
            if (player.getClientId().equals(request.getHeader().getClientId())) {
                response.setTeam(TeamConverter.convert(player.getTeam()));
                response.setPositionInTeam(player.getPositionInTeam());
            }
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        JoinGameResponse response = task.getContext().get(ContextConstants.RESPONSE, JoinGameResponse.class);
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
