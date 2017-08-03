package com.playtech.ptargame3.test.step;


import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TenStepState;
import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.step.common.AbstractStep;
import com.playtech.ptargame3.test.step.substeps.GetGameToJoinStep;
import com.playtech.ptargame3.test.step.substeps.Sleep500msStep;

import java.util.ArrayList;
import java.util.Collection;

public class GetGameToJoinWithWaitStep extends AbstractStep {
    public GetGameToJoinWithWaitStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public TaskState initialState() {
        return TenStepState.INITIAL;
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task task) {
        if (task.getCurrentState() == TenStepState.INITIAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new GetGameToJoinStep(getLogicResources()));
            return logics;
        } else if (task.getCurrentState() != TenStepState.FINAL) {
            String gameId = task.getContext().get(ContextConstants.JOIN_GAME_ID, String.class);
            if (StringUtil.isNull(gameId)) {
                ArrayList<Logic> logics = new ArrayList<>();
                logics.add(new Sleep500msStep(getLogicResources()));
                logics.add(new GetGameToJoinStep(getLogicResources()));
                return logics;
            }
        }
        return super.createStateSubLogics(task);
    }

    @Override
    public void execute(Task task) {
        if (task.getCurrentState() == TenStepState.FINAL) {
            String gameId = task.getContext().get(ContextConstants.JOIN_GAME_ID, String.class);
            if (StringUtil.isNull(gameId)) {
                throw new SystemException("Unable to find game to join");
            }
        }
    }
}
