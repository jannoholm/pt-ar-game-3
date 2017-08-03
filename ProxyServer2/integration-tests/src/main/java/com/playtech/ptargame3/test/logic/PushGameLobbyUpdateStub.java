package com.playtech.ptargame3.test.logic;

import com.playtech.ptargame3.api.lobby.PushGameLobbyUpdateMessage;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.ContextConstants;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PushGameLobbyUpdateStub extends AbstractTestLogic {
    public static final Logger logger = Logger.getLogger(PushGameLobbyUpdateStub.class.getName());

    public PushGameLobbyUpdateStub(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        PushGameLobbyUpdateMessage message = getInputMessage(task, PushGameLobbyUpdateMessage.class);
        Task testTask = getLogicResources().getGameRegistryStub().getGameTask(message.getGameId(), message.getHeader().getClientId());
        if (testTask != null) {
            testTask.getContext().put(ContextConstants.GAME_LOBBY_UPDATE, message);
            testTask.scheduleExecution();
        } else {
            logger.log(Level.FINE, "Unable to attach push to scenario.");
        }
    }
}
