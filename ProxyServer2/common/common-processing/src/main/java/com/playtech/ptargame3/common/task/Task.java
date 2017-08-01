package com.playtech.ptargame3.common.task;


public interface Task extends Runnable {
    TaskState getCurrentState();
    TaskContext getContext();
    void scheduleExecution();
}
