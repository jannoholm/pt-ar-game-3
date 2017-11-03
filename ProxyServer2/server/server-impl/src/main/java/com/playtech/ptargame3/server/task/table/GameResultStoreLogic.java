package com.playtech.ptargame3.server.task.table;

import com.playtech.ptargame3.api.lobby.Team;
import com.playtech.ptargame3.api.table.GameResultPlayerScore;
import com.playtech.ptargame3.api.table.GameResultStoreRequest;
import com.playtech.ptargame3.api.table.GameResultStoreResponse;
import com.playtech.ptargame3.api.table.SetUserInCarRequest;
import com.playtech.ptargame3.api.table.SetUserInCarResponse;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.task.AbstractLogic;

public class GameResultStoreLogic extends AbstractLogic {
    public GameResultStoreLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GameResultStoreRequest request = getInputRequest(task, GameResultStoreRequest.class);
        // TODO generating random result until implemented
        GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class);
        for (int i = 0; i < 4; ++i) {
            GameResultPlayerScore score = new GameResultPlayerScore();
            score.setTeam(Team.values()[i/2]);
            score.setPositionInTeam((byte)(i%2+1));
            score.setScore((int)(Math.random()*10000));
            score.setEloRating((int)(Math.random()*1000));
            score.setLeaderboardPosition((int)(Math.random()*100));
            response.addPlayerResult( score );
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GameResultStoreResponse response = task.getContext().get(ContextConstants.RESPONSE, GameResultStoreResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GameResultStoreResponse response = getResponse(task, GameResultStoreResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
