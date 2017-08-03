package com.playtech.ptargame3.test.registry;


import com.playtech.ptargame3.common.task.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSleepManager {

    public static final Logger logger = Logger.getLogger(TestSleepManager.class.getName());

    private final ScheduledExecutorService executor;
    private ScheduledFuture maintenanceFuture;

    private final TreeSet<TaskHolder> tasks = new TreeSet<>((o1, o2) -> (int)(o1.wakeup==o2.wakeup ? o1.hashCode()-o2.hashCode() : o1.wakeup-o2.wakeup));

    public TestSleepManager(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public void start() {
        if (maintenanceFuture == null) {
            maintenanceFuture = executor.scheduleAtFixedRate(() -> {
                // filter out actual timeouts
                ArrayList<TaskHolder> timed = new ArrayList<>();
                long current = System.currentTimeMillis();
                synchronized (tasks) {
                    Iterator<TaskHolder> i = tasks.iterator();
                    while (i.hasNext()) {
                        TaskHolder holder = i.next();
                        if (holder.wakeup <= current) {
                            timed.add(holder);
                            i.remove();
                        } else {
                            break;
                        }
                    }
                }

                // do something with timed out things
                for (TaskHolder holder : timed) {
                    logger.log(Level.FINE, ()->"Wakeup triggered for: " + holder.task.getContext().getInput().getId());
                    holder.task.scheduleExecution();
                }
            }, 500, 100, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Already started callback handler.");
        }
    }

    public void stop() {
        maintenanceFuture.cancel(true);
    }

    public void wakeupAt(Task task, long wakeup) {
        logger.fine(()->"Task scheduled for wakeupAt: " + task.getContext().getInput().getId());
        synchronized (tasks) {
            tasks.add(new TaskHolder(wakeup, task));
        }
    }

    private static class TaskHolder {
        private final long wakeup;
        private final Task task;

        private TaskHolder(long wakeup, Task task) {
            this.wakeup = wakeup;
            this.task = task;
        }

        @Override
        public String toString() {
            return task.getContext().getInput().getId();
        }
    }

}
