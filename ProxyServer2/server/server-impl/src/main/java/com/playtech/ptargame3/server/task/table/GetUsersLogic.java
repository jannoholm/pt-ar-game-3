package com.playtech.ptargame3.server.task.table;


import com.playtech.ptargame3.api.table.GetUsersRequest;
import com.playtech.ptargame3.api.table.GetUsersResponse;
import com.playtech.ptargame3.api.table.GetUsersUser;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.database.model.EloRating;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GetUsersLogic extends AbstractLogic {

    public GetUsersLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GetUsersRequest request = getInputRequest(task, GetUsersRequest.class);
        Collection<User> matchingUsers = getLogicResources().getDatabaseAccess().getUserDatabase().getUsers(request.getFilter());
        Map<Integer, Integer> leaderboard = getLeaderboard();

        // create response
        GetUsersResponse response = getResponse(task, GetUsersResponse.class);
        for (User user : matchingUsers) {
            if (!user.isHidden()) {
                GetUsersUser responseUser = new GetUsersUser();
                responseUser.setId(user.getId());
                responseUser.setName(user.getName());
                EloRating rating = getLogicResources().getDatabaseAccess().getRatingDatabase().getRating(user.getId());
                if (rating != null) {
                    responseUser.setEloRating(rating.getEloRating());
                }
                Integer position = leaderboard.get(user.getId());
                if (position != null) {
                    responseUser.setLeaderboardPosition(position);
                }

                response.addUser(responseUser);
            }
        }
        task.getContext().put(ContextConstants.RESPONSE, response);
    }

    private Map<Integer, Integer> getLeaderboard() {
        Map<Integer, Integer> leaderBoard = new HashMap<>();
        int pos = 0;
        for (EloRating rating : getLogicResources().getDatabaseAccess().getRatingDatabase().getLeaderboard()) {
            pos++;
            leaderBoard.put(rating.getUserId(), pos);
        }
        return leaderBoard;
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        GetUsersResponse response = task.getContext().get(ContextConstants.RESPONSE, GetUsersResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        GetUsersResponse response = getResponse(task, GetUsersResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
