package com.playtech.ptargame3.common.task;

public interface TaskState {
    TaskState next();
    boolean isFinal();
}
