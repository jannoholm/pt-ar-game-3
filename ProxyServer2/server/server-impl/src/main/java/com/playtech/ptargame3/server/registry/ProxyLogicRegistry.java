package com.playtech.ptargame3.server.registry;


import com.playtech.ptargame3.api.leaderboard.GetLeaderboardRequest;
import com.playtech.ptargame3.api.table.CarControlMessage;
import com.playtech.ptargame3.api.table.GameResultStoreRequest;
import com.playtech.ptargame3.api.table.GetUsersRequest;
import com.playtech.ptargame3.api.table.LocationNotificationMessage;
import com.playtech.ptargame3.api.game.GameControlMessage;
import com.playtech.ptargame3.api.game.GameUpdateBroadcardMessage;
import com.playtech.ptargame3.api.lobby.HostGameRequest;
import com.playtech.ptargame3.api.lobby.JoinGameRequest;
import com.playtech.ptargame3.api.lobby.GetDetailedGameInfoRequest;
import com.playtech.ptargame3.api.lobby.GetGamesRequest;
import com.playtech.ptargame3.api.table.SetUserInCarRequest;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicRegistry;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.server.task.game.CarControlLogic;
import com.playtech.ptargame3.server.task.game.GameControlLogic;
import com.playtech.ptargame3.server.task.game.GameUpdateBroadcastLogic;
import com.playtech.ptargame3.server.task.game.LocationNotificationLogic;
import com.playtech.ptargame3.server.task.game.PushGameLobbyUpdateLogic;
import com.playtech.ptargame3.server.task.lobby.GetDetailedGameInfoLogic;
import com.playtech.ptargame3.server.task.lobby.GetGamesLogic;
import com.playtech.ptargame3.server.task.lobby.HostGameLogic;
import com.playtech.ptargame3.server.task.lobby.JoinGameLogic;
import com.playtech.ptargame3.server.task.lobby.leaderboard.GetLeaderboardLogic;
import com.playtech.ptargame3.server.task.table.GameResultStoreLogic;
import com.playtech.ptargame3.server.task.table.GetUsersLogic;
import com.playtech.ptargame3.server.task.table.SetUserInCarLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyLogicRegistry implements LogicRegistry {

    private static final Logger logger = Logger.getLogger(ProxyLogicRegistry.class.getName());

    private LogicResources logicResources;

    private Map<String, Logic> messageToLogic = new HashMap<>();

    public void initialize(LogicResources logicResources) {
        this.logicResources = logicResources;
        addMapping(GetGamesRequest.class.getName(), GetGamesLogic.class);
        addMapping(HostGameRequest.class.getName(), HostGameLogic.class);
        addMapping(JoinGameRequest.class.getName(), JoinGameLogic.class);
        addMapping(GetDetailedGameInfoRequest.class.getName(), GetDetailedGameInfoLogic.class);
        addMapping(PushGameLobbyUpdateLogic.TASK_TYPE, PushGameLobbyUpdateLogic.class);
        addMapping(GameControlMessage.class.getName(), GameControlLogic.class);
        addMapping(GameUpdateBroadcardMessage.class.getName(), GameUpdateBroadcastLogic.class);
        addMapping(LocationNotificationMessage.class.getName(), LocationNotificationLogic.class);
        addMapping(CarControlMessage.class.getName(), CarControlLogic.class);
        addMapping(GetUsersRequest.class.getName(), GetUsersLogic.class);
        addMapping(SetUserInCarRequest.class.getName(), SetUserInCarLogic.class);
        addMapping(GameResultStoreRequest.class.getName(), GameResultStoreLogic.class);
        addMapping(GetLeaderboardRequest.class.getName(), GetLeaderboardLogic.class);
    }

    private void addMapping(String taskType, Class<? extends Logic> taskClass) {
        try {
            Logic logic = taskClass.getConstructor(LogicResources.class).newInstance(logicResources);
            messageToLogic.put(taskType, logic);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to initiate task of type: " + taskClass.getName(), e);
        }
    }

    @Override
    public Logic getLogic(String type) {
        return messageToLogic.get(type);
    }
}
