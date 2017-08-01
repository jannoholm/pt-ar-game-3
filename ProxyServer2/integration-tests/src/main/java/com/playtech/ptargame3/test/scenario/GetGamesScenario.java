package com.playtech.ptargame3.test.scenario;


import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.test.step.GetGamesStep;
import com.playtech.ptargame3.test.step.InitiateScenarioTimerStep;
import com.playtech.ptargame3.test.step.JoinStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetGamesScenario extends AbstractScenario {

    public GetGamesScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public List<Logic> createStateSubLogics(Task context) {
        if (context.getCurrentState() == OneStepState.INITIAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new InitiateScenarioTimerStep(getLogicResources()));
            logics.add(new JoinStep(getLogicResources()));
            logics.add(new GetGamesStep(getLogicResources()));
            return logics;
        } else {
            return Collections.emptyList();
        }
    }

}
