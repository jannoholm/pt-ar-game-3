package com.playtech.ptargame3.test.runner;


import com.playtech.ptargame3.test.scenario.CameraScenario;
import com.playtech.ptargame3.test.scenario.HostGameScenario;
import com.playtech.ptargame3.test.scenario.JoinGameScenario;
import com.playtech.ptargame3.test.scenario.TableScenario;

import java.io.IOException;
import java.util.logging.Logger;

public class TestOneTable extends AbstractTest {

    private static final Logger logger = Logger.getLogger(TestOneTable.class.getName());

    private void testOneRequest() throws IOException, InterruptedException {
        // start server and connector
        setupServer();
        setupConnector();
        setupScenarioRunner();

        // wait a little for startup
        sleep(100);

        // run one scenario
        scenarioRunner.runScenario(TableScenario.class, 1, 0);
        scenarioRunner.runScenario(CameraScenario.class, 1, 0);
        scenarioRunner.waitComplete(10000);

        logger.info("Unfinished tasks: " + scenarioRunner.getRunning());

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new TestOneTable().testOneRequest();
    }

}
