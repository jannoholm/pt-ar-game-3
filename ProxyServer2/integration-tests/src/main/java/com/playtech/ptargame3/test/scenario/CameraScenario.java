package com.playtech.ptargame3.test.scenario;


import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.test.step.JoinServerStep;
import com.playtech.ptargame3.test.step.LocationPushWithInterval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CameraScenario extends AbstractScenario {
    public CameraScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public Collection<Logic> createStateSubLogics(Task task) {
        if (task.getCurrentState() == TwoStepState.FINAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new JoinServerStep(getLogicResources(), JoinServerRequest.ClientType.CAMERA));
            logics.add(new LocationPushWithInterval(getLogicResources()));
            return logics;
        } else {
            return Collections.emptyList();
        }
    }
}
