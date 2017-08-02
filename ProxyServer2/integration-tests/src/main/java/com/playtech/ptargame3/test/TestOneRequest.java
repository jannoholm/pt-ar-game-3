package com.playtech.ptargame3.test;


import com.playtech.ptargame3.test.scenario.HostGameScenario;
import com.playtech.ptargame3.test.scenario.JoinGameScenario;

import java.io.IOException;
import java.util.logging.Logger;

public class TestOneRequest extends AbstractTest {

    private static final Logger logger = Logger.getLogger(TestOneRequest.class.getName());

    public void testOneRequest() throws IOException {
        // start server and connector
        setupServer();
        setupConnector();
        setupScenarioRunner();

        // wait a little for startup
        sleep(100);

        // run one scenario
        scenarioRunner.runScenario(HostGameScenario.class, 1, 0);
        scenarioRunner.waitComplete(60000);
        scenarioRunner.runScenario(JoinGameScenario.class, 1, 0);
        scenarioRunner.waitComplete(60000);

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new TestOneRequest().testOneRequest();
    }

}
