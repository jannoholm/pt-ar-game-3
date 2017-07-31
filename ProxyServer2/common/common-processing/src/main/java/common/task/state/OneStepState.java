package common.task.state;


import common.task.TaskState;

public enum OneStepState implements TaskState {
    INITIAL,
    FINAL;

    @Override
    public TaskState next() {
        TaskState next = null;
        if ( ordinal()+1 < values().length ) {
            next = values()[ordinal()+1];
        }
        return next;
    }

    @Override
    public boolean isFinal() {
        return ordinal()+1 == values().length;
    }
}
