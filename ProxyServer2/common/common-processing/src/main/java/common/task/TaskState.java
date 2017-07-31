package common.task;

public interface TaskState {
    TaskState next();
    boolean isFinal();
}
