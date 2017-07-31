package common.task;

public interface TaskFactory {
    Task getTask(TaskInput input);
    Task getTask(Logic logic, TaskInput input);
}
