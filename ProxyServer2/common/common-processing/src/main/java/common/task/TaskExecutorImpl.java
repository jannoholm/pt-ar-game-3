package common.task;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskExecutorImpl implements  TaskExecutor {

    public static final Logger logger = Logger.getLogger(TaskExecutorImpl.class.getName());

    private ExecutorService executor;

    public TaskExecutorImpl(final String prefix, int threads) {
        executor = Executors.newFixedThreadPool(threads, new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, prefix + "-" + (++count));
            }
        });
    }

    public void addTask(Task task) {
        executor.execute(task);
    }

    public void stop() {
        executor.shutdown();
    }

}
