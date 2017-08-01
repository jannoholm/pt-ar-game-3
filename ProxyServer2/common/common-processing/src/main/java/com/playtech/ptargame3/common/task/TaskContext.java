package com.playtech.ptargame3.common.task;

public interface TaskContext {
    TaskInput getInput();
    void put(Object key, Object value);
    Object get(Object key);
    void remove(Object key);
}
