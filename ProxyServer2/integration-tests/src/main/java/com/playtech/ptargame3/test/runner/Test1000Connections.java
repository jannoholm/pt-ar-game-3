package com.playtech.ptargame3.test.runner;


import com.playtech.ptargame3.test.scenario.HostGameScenario;
import com.playtech.ptargame3.test.scenario.JoinGameScenario;

import java.io.IOException;
import java.util.logging.Logger;

public class Test1000Connections extends AbstractTest {

    private static final Logger logger = Logger.getLogger(Test1000Connections.class.getName());

    public void testKeepAlive() throws IOException {
        // start server and connector
        setupServer();
        setupConnector();
        setupScenarioRunner();

        // wait a little for startup
        sleep(100);

        scenarioRunner.runScenario(HostGameScenario.class, 100, 50);
        scenarioRunner.runScenario(JoinGameScenario.class, 1000, 50);
        scenarioRunner.waitComplete(60000);

        logger.info("Unfinished tasks: " + scenarioRunner.getRunning());

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new Test1000Connections().testKeepAlive();
    }

}
