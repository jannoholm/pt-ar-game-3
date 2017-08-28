package com.playtech.ptargame3.test.scenario;


import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.test.step.JoinServerStep;
import com.playtech.ptargame3.test.step.substeps.SleepStep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TableScenario extends AbstractScenario {
    public TableScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task task) {
        if (task.getCurrentState() == TwoStepState.FINAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new JoinServerStep(getLogicResources(), JoinServerRequest.ClientType.TABLE));
            logics.add(new SleepStep(getLogicResources(),5000));
            return logics;
        } else {
            return Collections.emptyList();
        }
    }
}
