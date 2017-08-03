package com.playtech.ptargame3.common.task;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageTaskWrapper implements Task {

    private static final Logger logger = Logger.getLogger(MessageTaskWrapper.class.getName());

    private TaskExecutor executor;
    private TaskHolder mainTask;
    private Deque<TaskHolder> tasks = new LinkedList<>();

    public MessageTaskWrapper(TaskExecutor executor, Logic logic, TaskInput input) {
        this.executor = executor;
        this.mainTask = new TaskHolder(this, logic, input);
        this.tasks.add(mainTask);
    }

    @Override
    public TaskState getCurrentState() {
        return mainTask.currentState;
    }

    @Override
    public TaskContext getContext() {
        return mainTask.context;
    }

    @Override
    public void scheduleExecution() {
        this.executor.addTask(this);
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            Thread.currentThread().setName(threadName + "-" + mainTask.getContext().getInput().getId());
            TaskHolder current;
            while ((current = tasks.poll()) != null) {
                boolean done = current.runLogic();
                if (!done) {
                    tasks.addFirst(current);
                    boolean logicsAdded = addSubLogics(current);
                    if (!logicsAdded) {
                        break;
                    }
                } else {
                    if (current.isError()) {
                        tasks.clear();
                        mainTask.error = current.error;
                        mainTask.finalizeTask();
                    }
                }
            }
        } finally {
            Thread.currentThread().setName(threadName);
        }
    }

    private boolean addSubLogics(TaskHolder current) {
        Collection<Logic> newSubLogics = current.removeSubLogics();
        if (newSubLogics.size() > 0) {
            ArrayList<TaskHolder> tmp = new ArrayList<>(newSubLogics.size());
            for (Logic t : newSubLogics) {
                tmp.add(new TaskHolder(this, t, t.initialState(), mainTask.context));
            }
            for (int i = tmp.size()-1; i >= 0; i--) {
                tasks.addFirst(tmp.get(i));
            }
        }
        return newSubLogics.size() > 0;
    }

    private static class TaskHolder implements Task {
        private MessageTaskWrapper parent;
        private final Logic logic;
        private TaskState currentState;
        private TaskContext context;
        private Exception error;
        private Collection<Logic> subLogics = Collections.emptyList();
        private TaskState subLogicState;
        private boolean finalized = false;

        TaskHolder(MessageTaskWrapper parent, Logic logic, TaskInput input) {
            this(parent, logic, logic.initialState(), new TaskContextImpl(input));
        }

        TaskHolder(MessageTaskWrapper parent, Logic logic, TaskState currentState, TaskContext context) {
            this.parent = parent;
            this.logic = logic;
            this.currentState = currentState;
            this.context = context;
        }

        TaskHolder next() {
            return new TaskHolder(this.parent, this.logic, this.currentState.next(), this.context);
        }

        boolean isError() {
            return error != null;
        }

        Collection<Logic> removeSubLogics() {
            Collection<Logic> result = subLogics;
            if (!subLogics.isEmpty()) {
                subLogics = Collections.emptyList();
            }
            return result;
        }

        @Override
        public TaskState getCurrentState() {
            return this.currentState;
        }

        @Override
        public TaskContext getContext() {
            return this.context;
        }

        @Override
        public void scheduleExecution() {
            this.parent.scheduleExecution();
        }

        boolean runLogic() {
            while (true) {
                if (currentState == null || finalized) {
                    return true;
                } else if (currentState.isFinal() || isError()) {
                    finalizeTask();
                    return true;
                } else {
                    if (subLogicState == null) {
                        // sublogics for initial state
                        subLogics = logic.createStateSubLogics(this);
                        subLogicState = getCurrentState();
                    }
                    if (!subLogics.isEmpty()) {
                        return false;
                    }
                    TaskHolder next = next();
                    if (subLogicState != next.getCurrentState()) {
                        // sublogics for initial state
                        subLogics = logic.createStateSubLogics(next);
                        subLogicState = next.getCurrentState();
                    }
                    if (!subLogics.isEmpty()) {
                        return false;
                    }
                    if (logic.canExecute(next())) {
                        currentState = currentState.next();
                        try {
                            logic.execute(this);
                        } catch (Exception e) {
                            error = e;
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        void finalizeTask() {
            if (!finalized) {
                try {
                    if (error == null) {
                        logic.finishSuccess(this);
                    } else {
                        logic.finishError(this, error);
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Unable to finish logic.", e);
                }
                finalized = true;
            }
        }

        @Override
        public void run() {
            this.parent.run();
        }
    }
}
