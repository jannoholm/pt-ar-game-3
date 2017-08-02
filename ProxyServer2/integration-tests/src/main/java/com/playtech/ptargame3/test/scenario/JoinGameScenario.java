package com.playtech.ptargame3.test.scenario;


import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.OneStepState;
import com.playtech.ptargame3.test.step.GetGameToJoinStep;
import com.playtech.ptargame3.test.step.JoinGameStep;
import com.playtech.ptargame3.test.step.ValidateHostedGameStep;
import com.playtech.ptargame3.test.step.InitiateScenarioTimerStep;
import com.playtech.ptargame3.test.step.JoinServerStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinGameScenario extends AbstractScenario {

    public JoinGameScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public List<Logic> createStateSubLogics(Task context) {
        if (context.getCurrentState() == OneStepState.INITIAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new InitiateScenarioTimerStep(getLogicResources()));
            logics.add(new JoinServerStep(getLogicResources()));
            logics.add(new GetGameToJoinStep(getLogicResources()));
            logics.add(new JoinGameStep(getLogicResources()));
            return logics;
        } else {
            return Collections.emptyList();
        }
    }

}
