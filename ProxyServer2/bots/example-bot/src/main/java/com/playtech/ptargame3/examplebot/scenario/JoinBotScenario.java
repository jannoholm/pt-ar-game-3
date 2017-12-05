package com.playtech.ptargame3.examplebot.scenario;


import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.examplebot.scenario.steps.JoinServerStep;
import com.playtech.ptargame3.examplebot.scenario.steps.SetBotNameStep;
import com.playtech.ptargame3.examplebot.scenario.steps.SleepStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinBotScenario extends AbstractScenario {

    public JoinBotScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public List<Logic> createStateSubLogics(Task context) {
        if (context.getCurrentState() == TwoStepState.FINAL) {
            ArrayList<Logic> logics = new ArrayList<>();
            logics.add(new SetBotNameStep(getLogicResources(), "Example AI Bot"));
            logics.add(new JoinServerStep(getLogicResources(), JoinServerRequest.ClientType.BOT));
            logics.add(new SleepStep(getLogicResources(), 1000L*60*60*24*365*5)); // effectively never stop the scenario
            return logics;
        } else {
            return Collections.emptyList();
        }
    }

}
