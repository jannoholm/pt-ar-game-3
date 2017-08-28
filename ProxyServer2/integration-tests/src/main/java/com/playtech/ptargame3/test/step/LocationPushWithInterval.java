package com.playtech.ptargame3.test.step;


import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TenStepState;
import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.exception.FlowStopExcepton;
import com.playtech.ptargame3.test.step.common.AbstractStep;
import com.playtech.ptargame3.test.step.substeps.LocationPushStep;
import com.playtech.ptargame3.test.step.substeps.SleepStep;

import java.util.ArrayList;
import java.util.Collection;

public class LocationPushWithInterval extends AbstractStep {
    public LocationPushWithInterval(LogicResources logicResources) {
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
            logics.add(new LocationPushStep(getLogicResources()));
            return logics;
        } else if (task.getCurrentState() != TenStepState.FINAL) {
            String gameId = task.getContext().get(ContextConstants.JOIN_GAME_ID, String.class);
            if (StringUtil.isNull(gameId)) {
                ArrayList<Logic> logics = new ArrayList<>();
                logics.add(new SleepStep(getLogicResources(), 30));
                logics.add(new LocationPushStep(getLogicResources()));
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
                throw new FlowStopExcepton("Unable to find game to join.");
            }
        }
    }
}
