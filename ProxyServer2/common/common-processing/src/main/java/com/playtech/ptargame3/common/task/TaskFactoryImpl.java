package com.playtech.ptargame3.common.task;


public class TaskFactoryImpl implements TaskFactory {

    private final TaskExecutor executor;
    private final LogicRegistry logicRegistry;

    public TaskFactoryImpl(TaskExecutor executor, LogicRegistry logicRegistry) {
        this.executor = executor;
        this.logicRegistry = logicRegistry;
    }

    @Override
    public Task getTask(TaskInput input) {
        Logic logic = logicRegistry.getLogic(input.getType());
        if (logic == null) {
            throw new IllegalArgumentException("Unknown task input of type: " + input.getType());
        }
        return new MessageTaskWrapper(this.executor, logic, input);
    }

    @Override
    public Task getTask(Logic logic, TaskInput input) {
        return new MessageTaskWrapper(this.executor, logic, input);
    }
}
