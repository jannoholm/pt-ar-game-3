package test;


import common.task.Task;
import server.message.lobby.GetGamesRequest;
import server.session.ClientSession;
import test.scenario.GetGamesScenario;

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
        scenarioRunner.runScenario(GetGamesScenario.class, 1, 1);
        scenarioRunner.waitComplete(60000);

        // shutdown
        stopConnector();
        stopServer();
    }

    public static void main(String[] args) throws Exception {
        new TestOneRequest().testOneRequest();
    }

}
