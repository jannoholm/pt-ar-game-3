package com.playtech.ptargame3.server;

import com.playtech.ptargame3.api.ProxyMessageFactory;
import com.playtech.ptargame3.api.ProxyMessageParser;
import com.playtech.ptargame3.common.callback.CallbackHandlerImpl;
import com.playtech.ptargame3.common.io.NioServerListener;
import com.playtech.ptargame3.common.message.MessageParser;
import com.playtech.ptargame3.common.task.TaskExecutorImpl;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.common.task.TaskFactoryImpl;
import com.playtech.ptargame3.server.database.DatabaseAccessImpl;
import com.playtech.ptargame3.server.registry.GameRegistry;
import com.playtech.ptargame3.server.registry.ProxyClientRegistry;
import com.playtech.ptargame3.server.registry.ProxyLogicRegistry;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Starter {

    private void run() throws Exception {
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
        CallbackHandlerImpl callbackHandler = new CallbackHandlerImpl(clientRegistry, scheduledExecutorService);
        callbackHandler.start();
        TaskExecutorImpl taskExecutor = new TaskExecutorImpl("te", 2);
        ProxyLogicRegistry logicRegistry = new ProxyLogicRegistry();
        TaskFactory taskFactory = new TaskFactoryImpl(taskExecutor, logicRegistry);
        GameRegistry gameRegistry = new GameRegistry(scheduledExecutorService);
        DatabaseAccessImpl databaseAccess = new DatabaseAccessImpl(scheduledExecutorService);
        databaseAccess.setup();
        LogicResourcesImpl logicResources = new LogicResourcesImpl(callbackHandler, messageParser, clientRegistry, gameRegistry, taskFactory, databaseAccess);
        logicRegistry.initialize(logicResources);
        ProxyConnectionFactory connectionFactory = new ProxyConnectionFactory(messageParser, callbackHandler, clientRegistry, gameRegistry, taskFactory);

        NioServerListener proxy = new NioServerListener(connectionFactory, 8000);
        new Thread(proxy.start(), "proxy").start();

        WebListener web = new WebListener(8001, databaseAccess);
        web.start();
    }

    public static void main(String[] args) throws Exception {
        new Starter().run();
    }
}
