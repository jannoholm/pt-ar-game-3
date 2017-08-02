package com.playtech.ptargame3.common.task;

public interface TaskContext {
    TaskInput getInput();
    void put(Object key, Object value);
    <T> T get(Object key, Class<T> oClass);
    void remove(Object key);
}
