package test.step;


import common.task.LogicResources;
import common.task.Task;
import test.ContextConstants;

public class InitiateScenarioTimerStep extends AbstractStep {
    public InitiateScenarioTimerStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) throws Exception {
        task.getContext().put(ContextConstants.START_TIME, System.currentTimeMillis());
    }
}
