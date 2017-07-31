package common.task;


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
    public Object get(Object key) {
        return this.data.get(key);
    }

    @Override
    public void remove(Object key) {
        data.remove(key);
    }
}
