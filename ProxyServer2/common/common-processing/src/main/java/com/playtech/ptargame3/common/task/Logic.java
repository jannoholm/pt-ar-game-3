package com.playtech.ptargame3.common.task;


import java.util.Collection;

public interface Logic {
    TaskState initialState();
    LogicResources getLogicResources();
    Collection<Logic> createStateSubLogics(Task task);
    boolean canExecute(Task task);
    void execute(Task task);
    void finishSuccess(Task task);
    void finishError(Task task, Exception e);
}
