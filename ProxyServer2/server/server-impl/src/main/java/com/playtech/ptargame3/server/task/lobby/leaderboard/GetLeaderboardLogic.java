package com.playtech.ptargame3.server.task.lobby.leaderboard;


import com.playtech.ptargame3.api.leaderboard.GetLeaderboardRequest;
import com.playtech.ptargame3.api.leaderboard.GetLeaderboardResponse;
import com.playtech.ptargame3.api.leaderboard.GetLeaderboardUser;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.database.model.EloRating;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.util.Collection;

public class GetLeaderboardLogic extends AbstractLogic {
    public GetLeaderboardLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GetLeaderboardRequest request = getInputRequest(task, GetLeaderboardRequest.class);
        Collection<EloRating> leaderboard = getLogicResources().getDatabaseAccess().getRatingDatabase().getLeaderboard();

        // create response
        GetLeaderboardResponse response = getResponse(task, GetLeaderboardResponse.class);
        int pos = 0;
        for (EloRating rating : leaderboard) {
            User user = getLogicResources().getDatabaseAccess().getUserDatabase().getUser(rating.getUserId());
            if (user.isHidden() || user.getUserType() == User.UserType.REGULAR) continue;

            pos++;
            if (pos < 10 || request.getUserIds().contains(rating.getUserId())) {
                GetLeaderboardUser responseUser = new GetLeaderboardUser();
                responseUser.setUserId(rating.getUserId());
                responseUser.setName(user.getName());
                responseUser.setEloRating(rating.getEloRating());
                responseUser.setMatches(rating.getMatches());
                responseUser.setGoals(rating.getGoals());
                responseUser.setBulletHits(rating.getBulletHits());
                responseUser.setTotalScore(rating.getTotalScore());
                responseUser.setBallTouches(rating.getBallTouches());
                responseUser.setBoostTouches(rating.getBoostTouches());
                responseUser.setPosition(pos);
                response.addUser(responseUser);
            }
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GetLeaderboardResponse response = task.getContext().get(ContextConstants.RESPONSE, GetLeaderboardResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GetLeaderboardResponse response = getResponse(task, GetLeaderboardResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
