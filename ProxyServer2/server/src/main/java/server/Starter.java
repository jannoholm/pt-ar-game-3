package server;

import common.callback.CallbackHandlerImpl;
import common.io.NioServerListener;
import common.message.MessageParser;
import common.task.TaskExecutorImpl;
import server.message.ProxyMessageFactory;
import server.message.ProxyMessageParser;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Starter {

    public static void main(String[] args) throws Exception {
        ProxyMessageFactory messageFactory = new ProxyMessageFactory();
        messageFactory.initialize();
        MessageParser messageParser = new ProxyMessageParser(messageFactory);
        ProxyClientRegistry clientRegistry = new ProxyClientRegistry();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "back-" + count.addAndGet(1));
            }
        });
        CallbackHandlerImpl callbackHandler = new CallbackHandlerImpl(messageParser, clientRegistry, scheduledExecutorService);
        callbackHandler.start();
        LogicResourcesImpl logicResources = new LogicResourcesImpl(callbackHandler, messageParser, clientRegistry);
        TaskExecutorImpl taskExecutor = new TaskExecutorImpl("te", 2);
        MessageTaskFactory messageTaskFactory = new MessageTaskFactory(taskExecutor, logicResources);
        messageTaskFactory.initialize();
        ProxyConnectionFactory connectionFactory = new ProxyConnectionFactory(messageParser, callbackHandler, clientRegistry, messageTaskFactory);

        NioServerListener proxy = new NioServerListener(connectionFactory, 8000);
        new Thread(proxy.start(), "proxy").start();

        WebListener web = new WebListener(8001);
        web.start();
    }
}
