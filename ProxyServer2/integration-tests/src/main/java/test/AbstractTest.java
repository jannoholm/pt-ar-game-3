package test;

import common.callback.CallbackHandlerImpl;
import common.io.NioServerConnector;
import common.io.NioServerListener;
import common.message.MessageParser;
import common.task.TaskExecutorImpl;
import server.LogicResourcesImpl;
import server.MessageTaskFactory;
import server.ProxyClientRegistry;
import server.ProxyConnectionFactory;
import server.ProxyLogicResources;
import server.message.ProxyMessageFactory;
import server.message.ProxyMessageParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AbstractTest {

    protected MessageParser messageParser;
    private ScheduledExecutorService maintenanceService;

    protected NioServerListener proxy;
    protected CallbackHandlerImpl proxyCallbackHandler;
    protected TaskExecutorImpl proxyTaskExecutor;

    protected NioServerConnector connector;
    protected CallbackHandlerImpl connectorCallbackHandler;
    protected ProxyLogicResources connectorLogicResources;
    protected ConnectorTaskFactoryStub connectorTaskFactory;
    protected TaskExecutorImpl connectorTaskExecutor;

    protected ScenarioFactory scenarioFactory;
    protected ScenarioRunner scenarioRunner;

    public AbstractTest() {
        maintenanceService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "back-" + count.addAndGet(1));
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    protected void setupMessageParser() {
        if (messageParser == null) {
            ProxyMessageFactory messageFactory = new ProxyMessageFactory();
            messageFactory.initialize();
            messageParser = new ProxyMessageParser(messageFactory);
        }
    }

    protected void setupServer() throws IOException {
        // initialize parser
        setupMessageParser();

        // callback and task processing
        ProxyClientRegistry clientRegistry = new ProxyClientRegistry();
        proxyCallbackHandler = new CallbackHandlerImpl(messageParser, clientRegistry, maintenanceService);
        proxyCallbackHandler.start();
        proxyTaskExecutor = new TaskExecutorImpl("te", 2);
        LogicResourcesImpl logicResources = new LogicResourcesImpl(proxyCallbackHandler, messageParser, clientRegistry);
        MessageTaskFactory messageTaskFactory = new MessageTaskFactory(proxyTaskExecutor, logicResources);
        messageTaskFactory.initialize();
        ProxyConnectionFactory connectionFactory = new ProxyConnectionFactory(messageParser, proxyCallbackHandler, clientRegistry, messageTaskFactory);

        // setup server
        proxy = new NioServerListener(connectionFactory, 8000);
        new Thread(proxy.start(),"proxy").start();
    }

    protected void stopServer() throws IOException {
        proxy.stop();
        proxyCallbackHandler.stop();
        proxyTaskExecutor.stop();
    }

    protected void setupConnector() {
        // initialize parser
        setupMessageParser();

        // callback and task processing
        ProxyClientRegistry clientRegistry = new ProxyClientRegistry();
        connectorCallbackHandler = new CallbackHandlerImpl(messageParser, clientRegistry, maintenanceService);
        connectorCallbackHandler.start();
        connectorLogicResources = new LogicResourcesImpl(connectorCallbackHandler, messageParser, clientRegistry);
        connectorTaskExecutor = new TaskExecutorImpl("ct", 2);
        connectorTaskFactory = new ConnectorTaskFactoryStub(connectorTaskExecutor, connectorLogicResources);
        connectorTaskFactory.initialize();
        TestConnectionFactory connectorFactory = new TestConnectionFactory(messageParser, connectorCallbackHandler, clientRegistry, connectorTaskFactory);

        // setup connector
        connector = new NioServerConnector(connectorFactory);
        new Thread(connector.start(),"connector").start();
    }

    protected void stopConnector() {
        connector.stop();
        connectorCallbackHandler.stop();
        connectorTaskExecutor.stop();
    }

    protected InetSocketAddress getServerAddress() {
        return new InetSocketAddress("127.0.0.1", 8000);
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {}
    }

    protected void setupScenarioRunner() {
        this.scenarioFactory = new ScenarioFactory(connectorTaskExecutor, connectorLogicResources, connector);
        this.scenarioRunner = new ScenarioRunner(scenarioFactory);
    }
}
