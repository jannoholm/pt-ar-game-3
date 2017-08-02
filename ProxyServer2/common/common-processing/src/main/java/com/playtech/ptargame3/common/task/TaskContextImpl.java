package com.playtech.ptargame3.common.task;


import java.util.HashMap;
import java.util.Map;

public class TaskContextImpl implements TaskContext {

    private final TaskInput input;
    private final Map<Object, Object> data;

    public TaskContextImpl(TaskInput input) {
        this.input = input;
        this.data = new HashMap<>();
    }

    @Override
    public TaskInput getInput() {
        return this.input;
    }

    @Override
    public void put(Object key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public <T> T get(Object key, Class<T> oClass) {
        Object result = this.data.get(key);
        if (result == null) {
            return null;
        } else if (oClass.isInstance(result)) {
            return oClass.cast(result);
        } else {
            throw new IllegalArgumentException("Invalid expected type. Actual: " + result.getClass().getName() + ", expected: " + oClass.getName());
        }
    }

    @Override
    public void remove(Object key) {
        data.remove(key);
    }
}
