package com.playtech.ptargame3.examplebot.scenario;

import com.playtech.ptargame3.common.task.TaskInput;

import java.util.concurrent.atomic.AtomicInteger;

public class ScenarioTaskInput implements TaskInput {

    private static final AtomicInteger counter=new AtomicInteger(50000);
    private String type;
    private String id;

    public ScenarioTaskInput(String type) {
        this.type = type;
        this.id = type + "-" + counter.incrementAndGet();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
