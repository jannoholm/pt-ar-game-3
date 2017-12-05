package com.playtech.ptargame3.examplebot;

import com.playtech.ptargame3.api.ProxyMessageFactory;
import com.playtech.ptargame3.api.ProxyMessageParser;
import com.playtech.ptargame3.common.callback.CallbackHandlerImpl;
import com.playtech.ptargame3.common.io.NioServerConnector;
import com.playtech.ptargame3.common.task.TaskExecutorImpl;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.common.task.TaskFactoryImpl;
import com.playtech.ptargame3.examplebot.logic.BotLogicRegistry;
import com.playtech.ptargame3.examplebot.logic.BotLogicResourcesImpl;
import com.playtech.ptargame3.examplebot.scenario.ScenarioFactory;
import com.playtech.ptargame3.examplebot.scenario.ScenarioRunner;
import com.playtech.ptargame3.examplebot.scenario.SleepManager;
import com.playtech.ptargame3.examplebot.scenario.JoinBotScenario;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExampleBot {

    private void run() {
        // initialize parser
        ProxyMessageFactory messageFactory = new ProxyMessageFactory();
        messageFactory.initialize();
        ProxyMessageParser messageParser = new ProxyMessageParser(messageFactory);

        ScheduledExecutorService maintenanceService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "back-" + count.addAndGet(1));
                thread.setDaemon(true);
                return thread;
            }
        });

        // callback and task processing
        BotClientRegistry clientRegistry = new BotClientRegistry();
        CallbackHandlerImpl callbackHandler = new CallbackHandlerImpl(clientRegistry, maintenanceService);
        callbackHandler.start();
        BotLogicRegistry testLogicRegistry = new BotLogicRegistry();
        TaskExecutorImpl taskExecutor = new TaskExecutorImpl("ct", 2);
        TaskFactory connectorTaskFactory = new TaskFactoryImpl(taskExecutor, testLogicRegistry);
        SleepManager sleepManager = new SleepManager(maintenanceService);
        sleepManager.start();
        BotLogicResourcesImpl logicResources = new BotLogicResourcesImpl(callbackHandler, messageParser, clientRegistry, sleepManager);
        testLogicRegistry.initialize(logicResources);
        BotConnectionFactory connectorFactory = new BotConnectionFactory(messageParser, callbackHandler, clientRegistry, connectorTaskFactory);

        // setup connector
        NioServerConnector connector = new NioServerConnector(connectorFactory);
        new Thread(connector.start(), "connector").start();

        // open connection
        ScenarioRunner runner = new ScenarioRunner(new ScenarioFactory(taskExecutor, logicResources, connector));
        runner.runScenario(JoinBotScenario.class, 1, 0);
        runner.waitComplete(Long.MAX_VALUE);

        // stop
        connector.stop();
        callbackHandler.stop();
        taskExecutor.stop();
    }

    public static void main(String[] args) {
        new ExampleBot().run();

    }
}
