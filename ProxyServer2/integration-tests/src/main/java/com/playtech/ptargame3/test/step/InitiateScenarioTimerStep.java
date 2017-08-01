package com.playtech.ptargame3.test.step;


import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.ContextConstants;

public class InitiateScenarioTimerStep extends AbstractStep {
    public InitiateScenarioTimerStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) throws Exception {
        task.getContext().put(ContextConstants.START_TIME, System.currentTimeMillis());
    }
}
