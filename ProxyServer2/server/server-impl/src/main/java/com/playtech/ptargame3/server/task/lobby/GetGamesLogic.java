package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.api.lobby.GetGamesGameInfo;
import com.playtech.ptargame3.api.lobby.GetGamesRequest;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.api.lobby.GetGamesResponse;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.util.ArrayList;
import java.util.Collection;


public class GetGamesLogic extends AbstractLogic {

    public GetGamesLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GetGamesRequest request = getInputRequest(task, GetGamesRequest.class);
        Collection<GameRegistryGame> matchingGames = getLogicResources().getGameRegistry().findGames(request.getFilter(), request.isAll());

        // create response
        GetGamesResponse response = getResponse(task, GetGamesResponse.class);
        for (GameRegistryGame game : matchingGames) {
            GetGamesGameInfo responseGameInfo = new GetGamesGameInfo();
            responseGameInfo.setGameId(game.getGameId());
            responseGameInfo.setGameName(game.getGameName());
            responseGameInfo.setFreePlaces(game.getPositions()-game.getPlayers().size());
            responseGameInfo.setTotalPlaces(game.getPositions());
            responseGameInfo.setAiType(game.getAiType());
            response.addGame(responseGameInfo);
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GetGamesResponse response = task.getContext().get(ContextConstants.RESPONSE, GetGamesResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GetGamesResponse response = getResponse(task, GetGamesResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
