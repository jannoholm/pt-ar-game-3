package test;


import common.io.NioServerConnector;
import common.task.LogicResources;
import common.task.MessageTaskWrapper;
import common.task.Task;
import common.task.TaskExecutor;
import server.exception.SystemException;
import test.scenario.AbstractScenario;

import java.util.logging.Logger;

public class ScenarioFactory {
    private static final Logger logger = Logger.getLogger(ScenarioFactory.class.getName());

    private final TaskExecutor executor;
    private final LogicResources logicResources;
    private final NioServerConnector connector;

    public ScenarioFactory(TaskExecutor executor, LogicResources logicResources, NioServerConnector connector) {
        this.executor = executor;
        this.logicResources = logicResources;
        this.connector = connector;
    }

    public <T extends AbstractScenario> Task getTask(Class<T> scenarioClass) {
        T scenario;
        try {
            scenario = scenarioClass.getConstructor(LogicResources.class).newInstance(logicResources);
        } catch (Exception e) {
            throw new SystemException("Unable to create scenario: " + scenarioClass.getName());
        }
        MessageTaskWrapper task = new MessageTaskWrapper(this.executor, scenario, new ScenarioTaskInput(scenarioClass.getSimpleName()));
        task.getContext().put(ContextConstants.CONNECTOR, connector);
        return task;
    }

}
