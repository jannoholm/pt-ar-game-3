package com.playtech.ptargame3.test.runner;


import com.playtech.ptargame3.test.scenario.HostGameScenario;

import java.io.IOException;
import java.util.logging.Logger;

public class EmulateClients extends AbstractTest {

    private static final Logger logger = Logger.getLogger(TestOneGame.class.getName());

    private void emulate() throws IOException, InterruptedException {
        // start connector
        setupConnector();
        setupScenarioRunner();

        // wait a little for startup
        sleep(100);

        // run one scenario
        scenarioRunner.runScenario(HostGameScenario.class, 1, 0);
        scenarioRunner.waitComplete(36000000);

        logger.info("Unfinished tasks: " + scenarioRunner.getRunning());

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new EmulateClients().emulate();
    }
}
