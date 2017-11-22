package com.playtech.ptargame3.server;

import com.playtech.ptargame3.api.ProxyMessageFactory;
import com.playtech.ptargame3.api.ProxyMessageParser;
import com.playtech.ptargame3.common.callback.CallbackHandlerImpl;
import com.playtech.ptargame3.common.io.NioServerListener;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskExecutorImpl;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.common.task.TaskFactoryImpl;
import com.playtech.ptargame3.server.ai.GameLogImpl;
import com.playtech.ptargame3.server.database.DatabaseAccessImpl;
import com.playtech.ptargame3.server.registry.GameRegistry;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;
import com.playtech.ptargame3.server.registry.ProxyLogicRegistry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Starter {

    private static final Starter starter = new Starter();

    private NioServerListener proxyListener;
    private WebListener webListener;
    private TaskExecutorImpl taskExecutor;

    private void run() throws Exception {
        ProxyMessageFactory messageFactory = new ProxyMessageFactory();
        messageFactory.initialize();
        MessageParser messageParser = new ProxyMessageParser(messageFactory);
        ProxyClientRegistry clientRegistry = new ProxyClientRegistry();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "back-" + count.addAndGet(1));
                thread.setDaemon(true);
                return thread;
            }
        });
        CallbackHandlerImpl callbackHandler = new CallbackHandlerImpl(clientRegistry, scheduledExecutorService);
        callbackHandler.start();
        taskExecutor = new TaskExecutorImpl("te", 2);
        ProxyLogicRegistry logicRegistry = new ProxyLogicRegistry();
        TaskFactory taskFactory = new TaskFactoryImpl(taskExecutor, logicRegistry);
        GameRegistry gameRegistry = new GameRegistry(scheduledExecutorService);
        DatabaseAccessImpl databaseAccess = new DatabaseAccessImpl(scheduledExecutorService);
        databaseAccess.setup();
        GameLogImpl gamelog = new GameLogImpl(scheduledExecutorService);
        gamelog.init();
        LogicResourcesImpl logicResources = new LogicResourcesImpl(callbackHandler, messageParser, clientRegistry, gameRegistry, taskFactory, databaseAccess, gamelog);
        logicRegistry.initialize(logicResources);
        ProxyConnectionFactory connectionFactory = new ProxyConnectionFactory(messageParser, callbackHandler, clientRegistry, gameRegistry, taskFactory);

        proxyListener = new NioServerListener(connectionFactory, 8000);
        new Thread(proxyListener.start(), "proxyListener").start();

        webListener = new WebListener(8001, databaseAccess);
        webListener.start();
    }

    private void stop() {
        if (proxyListener != null) {
            proxyListener.stop();
        }
        if (webListener != null) {
            webListener.stop();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignore) {}
        taskExecutor.stop();
    }

    public static void main(String[] args) throws Exception {
        start(args);
    }

    public static void start(String[] args) throws Exception {
        starter.run();
    }
    public static void stop(String[] args) throws Exception {
        starter.stop();
    }
}
