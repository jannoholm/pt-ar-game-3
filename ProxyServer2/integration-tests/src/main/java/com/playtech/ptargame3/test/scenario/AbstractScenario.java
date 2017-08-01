package com.playtech.ptargame3.test.scenario;

import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.ScenarioRunner;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractScenario extends AbstractLogic {

    public static final Logger logger = Logger.getLogger(AbstractScenario.class.getName());

    public AbstractScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public final void execute(Task task) {
        // scenario relies on subtasks. it does not have logic itself
    }

    @Override
    public final void finishSuccess(Task task) {
        finishScenario(task, null);
    }

    @Override
    public final void finishError(Task task, Exception e) {
        finishScenario(task, e);
    }

    protected void finishScenario(Task task, Exception e) {
        try {
            // log result
            Long startTime = (Long)task.getContext().get(ContextConstants.START_TIME);
            logger.log(Level.INFO, getClass().getName() + " finished with " + (e == null ? "SUCCESS" : "FAILURE") + " in " + (startTime != null ? System.currentTimeMillis()-startTime : "NaN") + "ms.", e);

            // notify runner
            ScenarioRunner runner = (ScenarioRunner)task.getContext().get(ContextConstants.SCENARIO_RUNNER);
            if (runner != null) {
                runner.notifyFinish(task);
            }
        } finally {
            closeSession(task);
        }
    }

    protected void closeSession(Task task) {
        Session session = (Session)task.getContext().get(ContextConstants.SESSION);
        if (session != null) {
            session.close();
        }

    }
}
