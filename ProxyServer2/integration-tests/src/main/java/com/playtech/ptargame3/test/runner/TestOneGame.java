package com.playtech.ptargame3.test.runner;


import com.playtech.ptargame3.test.scenario.HostGameScenario;
import com.playtech.ptargame3.test.scenario.JoinGameScenario;

import java.io.IOException;
import java.util.logging.Logger;

public class TestOneGame extends AbstractTest {

    private static final Logger logger = Logger.getLogger(TestOneGame.class.getName());

    private void testOneRequest() throws IOException, InterruptedException {
        // start server and connector
        setupServer();
        setupConnector();
        setupScenarioRunner();

        // wait a little for startup
        sleep(100);

        // run one scenario
        scenarioRunner.runScenario(HostGameScenario.class, 1, 0);
        scenarioRunner.runScenario(JoinGameScenario.class, 10, 0);
        scenarioRunner.waitComplete(10000);

        logger.info("Unfinished tasks: " + scenarioRunner.getRunning());

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new TestOneGame().testOneRequest();
    }

}
