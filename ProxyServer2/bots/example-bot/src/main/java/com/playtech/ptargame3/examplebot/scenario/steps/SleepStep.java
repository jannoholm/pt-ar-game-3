package com.playtech.ptargame3.examplebot.scenario.steps;

import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.examplebot.logic.ContextConstants;

import java.util.logging.Logger;


public class SleepStep extends AbstractStep {
    private static final Logger logger = Logger.getLogger(SleepStep.class.getName());

    private final long sleepTime;

    public SleepStep(LogicResources logicResources, long sleepTime) {
        super(logicResources);
        this.sleepTime = sleepTime;
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    public boolean canExecute(Task task) {
        if (task.getCurrentState() == TwoStepState.FINAL) {
            long wakeup = task.getContext().get(ContextConstants.WAKEUP_TIME, Long.class);
            return wakeup <= System.currentTimeMillis();
        } else {
            return true;
        }
    }

    @Override
    public void execute(Task task) {
        if (task.getCurrentState() == TwoStepState.MIDDLE) {
            long wakeup = System.currentTimeMillis() + sleepTime;
            task.getContext().put(ContextConstants.WAKEUP_TIME, wakeup);
            getLogicResources().getSleepManager().wakeupAt(task, wakeup);
        }
    }
}
