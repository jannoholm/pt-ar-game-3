package com.playtech.ptargame3.test.step;

import com.playtech.ptargame3.api.lobby.GameStatus;
import com.playtech.ptargame3.api.lobby.PushGameLobbyUpdateMessage;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.step.common.AbstractStep;
import com.playtech.ptargame3.test.step.substeps.RegisterForGameLobbyUpdateStep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class WaitGameStartStep extends AbstractStep {
    public WaitGameStartStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task task) {
        if (task.getCurrentState() == OneStepState.INITIAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new RegisterForGameLobbyUpdateStep(getLogicResources()));
            return logics;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean canExecute(Task task) {
        if (task.getCurrentState() == OneStepState.FINAL) {
            PushGameLobbyUpdateMessage update = task.getContext().get(ContextConstants.GAME_LOBBY_UPDATE, PushGameLobbyUpdateMessage.class);
            if (update != null && update.getGameStatus() == GameStatus.PLAYING) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Task task) {
        // unregister
        String gameId = task.getContext().get(ContextConstants.PLAY_GAME_ID, String.class);
        String clientId = task.getContext().get(ContextConstants.CLIENT_ID, String.class);
        getLogicResources().getGameRegistryStub().removeGameTask(gameId, clientId);
    }
}
