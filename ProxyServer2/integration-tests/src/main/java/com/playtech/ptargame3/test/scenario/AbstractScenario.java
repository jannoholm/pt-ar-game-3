package com.playtech.ptargame3.test.scenario;

import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.test.logic.AbstractTestLogic;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.runner.ScenarioRunner;
import com.playtech.ptargame3.test.exception.FlowStopExcepton;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractScenario extends AbstractTestLogic {

    private static final Logger logger = Logger.getLogger(AbstractScenario.class.getName());

    private enum EndStatus {
        SUCCESS,
        FAILURE,
        CANCELLED
    }

    public AbstractScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    public final void execute(Task task) {
        if (task.getCurrentState() == TwoStepState.MIDDLE) {
            task.getContext().put(ContextConstants.START_TIME, System.currentTimeMillis());
        }
    }

    @Override
    public final void finishSuccess(Task task) {
        finishScenario(task, null);
    }

    @Override
    public final void finishError(Task task, Exception e) {
        finishScenario(task, e);
    }

    private void finishScenario(Task task, Exception e) {
        try {
            // log result
            Long startTime = task.getContext().get(ContextConstants.START_TIME, Long.class);
            String finishTime = String.valueOf(startTime != null ? System.currentTimeMillis()-startTime : "NaN");
            EndStatus endStatus = (e == null ? EndStatus.SUCCESS : (e instanceof FlowStopExcepton ? EndStatus.CANCELLED : EndStatus.FAILURE));
            logger.log(Level.INFO, getClass().getName() + " finished with " + endStatus + " in " + finishTime + "ms.", endStatus == EndStatus.CANCELLED ? null : e);

            // notify runner
            ScenarioRunner runner = task.getContext().get(ContextConstants.SCENARIO_RUNNER, ScenarioRunner.class);
            if (runner != null) {
                runner.notifyFinish(task);
            }
        } finally {
            closeSession(task);
        }
    }

    private void closeSession(Task task) {
        Session session = task.getContext().get(ContextConstants.SESSION, Session.class);
        if (session != null) {
            session.close();
        }

    }
}
