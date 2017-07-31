package test.scenario;

import common.session.Session;
import common.task.LogicResources;
import common.task.Task;
import server.task.AbstractLogic;
import test.ContextConstants;
import test.ScenarioRunner;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractScenario extends AbstractLogic {

    public static final Logger logger = Logger.getLogger(AbstractScenario.class.getName());

    public AbstractScenario(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public final void execute(Task task) throws Exception {
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
