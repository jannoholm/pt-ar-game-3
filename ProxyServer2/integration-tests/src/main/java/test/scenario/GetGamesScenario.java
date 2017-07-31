package test.scenario;


import common.task.Logic;
import common.task.LogicResources;
import common.task.Task;
import common.task.state.OneStepState;
import test.step.GetGamesStep;
import test.step.InitiateScenarioTimerStep;
import test.step.JoinStep;

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
