package com.playtech.ptargame3.test.runner;

import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.ContextConstants;
import com.playtech.ptargame3.test.scenario.ScenarioFactory;
import com.playtech.ptargame3.test.scenario.AbstractScenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScenarioRunner {
    private final ScenarioFactory scenarioFactory;

    private final Map<String, Task> running = new HashMap<>();

    public ScenarioRunner(ScenarioFactory scenarioFactory) {
        this.scenarioFactory = scenarioFactory;
    }

    private void addTasks(Collection<Task> tasks) {
        synchronized (running) {
            for (Task task : tasks) {
                running.put(task.getContext().getInput().getId(), task);
            }
        }
    }

    private void removeTask(Task task) {
        synchronized (running) {
            running.remove(task.getContext().getInput().getId());
            running.notifyAll();
        }
    }

    public Collection<String> getRunning() {
        synchronized (running) {
            return new ArrayList<>(running.keySet());
        }
    }

    public <T extends AbstractScenario> Collection<Task> runScenario(Class<T> scenario, int times, int ramp_up) {
        ArrayList<Task> tasks = new ArrayList<>(times);
        for (int i = 0; i < times; ++i) {
            Task scenarioTask = this.scenarioFactory.getTask(scenario);
            scenarioTask.getContext().put(ContextConstants.SCENARIO_RUNNER, this);
            tasks.add(scenarioTask);
        }

        addTasks(tasks);

        for (Task task : tasks) {
            task.scheduleExecution();
            try {
                Thread.sleep(ramp_up);
            } catch (InterruptedException ignore) {}
        }

        return Collections.unmodifiableCollection(tasks);
    }

    public void notifyFinish(Task task) {
        removeTask(task);
    }

    public void waitComplete(long timeout) {
        long start = System.currentTimeMillis();
        synchronized (running) {
            while (System.currentTimeMillis()-start < timeout && !running.isEmpty()) {
                try {
                    running.wait(timeout);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
