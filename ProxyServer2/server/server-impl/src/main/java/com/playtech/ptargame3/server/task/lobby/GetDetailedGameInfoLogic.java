package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.api.lobby.GetDetailedGameInfoRequest;
import com.playtech.ptargame3.api.lobby.GetDetailedGameInfoResponse;
import com.playtech.ptargame3.api.lobby.PendingGamePlayer;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.exception.GameNotFoundException;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.registry.GameRegistryGamePlayer;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.util.TeamConverter;


public class GetDetailedGameInfoLogic extends AbstractLogic {
    public GetDetailedGameInfoLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GetDetailedGameInfoRequest request = getInputRequest(task, GetDetailedGameInfoRequest.class);
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(request.getGameId());
        if (game == null) throw new GameNotFoundException("Game not found: " + request.getGameId());
        GetDetailedGameInfoResponse response = getLogicResources().getMessageParser().createResponse(request, GetDetailedGameInfoResponse.class);
        response.setAiType(game.getAiType());
        response.setFreePlaces(game.getPositions()-game.getPlayers().size());
        response.setTotalPlaces(game.getPositions());
        response.setGameName(game.getGameName());
        for (GameRegistryGamePlayer player : game.getPlayers()) {
            PendingGamePlayer rider = new PendingGamePlayer();
            rider.setClientId(player.getClientId());
            rider.setName(getLogicResources().getClientRegistry().getName(player.getClientId()));
            rider.setPositionInTeam(player.getPositionInTeam());
            rider.setTeam(TeamConverter.convert(player.getTeam()));
            response.addPlayer(rider);
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GetDetailedGameInfoResponse response = task.getContext().get(ContextConstants.RESPONSE, GetDetailedGameInfoResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GetDetailedGameInfoResponse response = getResponse(task, GetDetailedGameInfoResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
