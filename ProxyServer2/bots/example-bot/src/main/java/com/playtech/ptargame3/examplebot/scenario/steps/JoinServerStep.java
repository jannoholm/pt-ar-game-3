package com.playtech.ptargame3.examplebot.scenario.steps;


import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.api.general.JoinServerResponse;
import com.playtech.ptargame3.common.callback.CallbackHandler;
import com.playtech.ptargame3.common.io.NioServerConnector;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskState;
import com.playtech.ptargame3.common.task.state.TwoStepState;
import com.playtech.ptargame3.examplebot.BotSession;
import com.playtech.ptargame3.examplebot.RuntimeInfo;
import com.playtech.ptargame3.examplebot.SystemException;
import com.playtech.ptargame3.examplebot.logic.ContextConstants;

import java.net.InetSocketAddress;

public class JoinServerStep extends AbstractStep {

    private final JoinServerRequest.ClientType clientType;

    public JoinServerStep(LogicResources logicResources, JoinServerRequest.ClientType clientType) {
        super(logicResources);
        this.clientType = clientType;
    }

    @Override
    public TaskState initialState() {
        return TwoStepState.INITIAL;
    }

    @Override
    public boolean canExecute(Task task) {
        if (task.getCurrentState() == TwoStepState.FINAL) {
            JoinServerRequest joinServerRequest = task.getContext().get(ContextConstants.CALLBACK_REQUEST, JoinServerRequest.class);
            if (joinServerRequest == null) {
                throw new SystemException("join request not set. Unable to proceed");
            } else {
                CallbackHandler.ResponseStatus status = getLogicResources().getCallbackHandler().getResponseStatus(task, joinServerRequest);
                return status != CallbackHandler.ResponseStatus.PENDING;
            }
        }
        return true;
    }

    @Override
    public void execute(Task task) {
        if (task.getCurrentState() == TwoStepState.MIDDLE) {
            // create a new connection
            NioServerConnector connector = task.getContext().get(ContextConstants.CONNECTOR, NioServerConnector.class);
            BotSession session = (BotSession) connector.connect(getServerAddress());
            task.getContext().put(ContextConstants.SESSION, session);

            // get name
            String clientName = task.getContext().get(ContextConstants.CLIENT_NAME, String.class);

            // join
            JoinServerRequest joinServerRequest = createMessage(task, JoinServerRequest.class);
            joinServerRequest.getHeader().setClientId(RuntimeInfo.INSTANCE.getClientId());
            joinServerRequest.setName(clientName);
            joinServerRequest.setEmail("bot@playtech.com");
            joinServerRequest.setClientType(clientType);
            getLogicResources().getCallbackHandler().sendCallback(task, joinServerRequest, session);
            task.getContext().put(ContextConstants.CALLBACK_REQUEST, joinServerRequest);
        } else if (task.getCurrentState() == TwoStepState.FINAL) {
            JoinServerRequest joinServerRequest = task.getContext().get(ContextConstants.CALLBACK_REQUEST, JoinServerRequest.class);
            CallbackHandler.ResponseStatus status = getLogicResources().getCallbackHandler().getResponseStatus(task, joinServerRequest);
            if (status == CallbackHandler.ResponseStatus.SUCCESS) {
                JoinServerResponse joinServerResponse = (JoinServerResponse)getLogicResources().getCallbackHandler().getResponse(task, joinServerRequest);
                BotSession session = task.getContext().get(ContextConstants.SESSION, BotSession.class);
                String clientId = joinServerResponse.getHeader().getClientId();
                session.setClientId(clientId);
                task.getContext().put(ContextConstants.CLIENT_ID, clientId);
//                getLogicResources().getClientRegistry().addClientConnection(
//                        clientId,
//                        joinServerRequest.getName(),
//                        joinServerRequest.getEmail(),
//                        ClientTypeConverter.convert(joinServerRequest.getClientType()),
//                        session
//                );
            } else {
                throw new SystemException("Join failed with response status: " + status);
            }
        }
    }

    private InetSocketAddress getServerAddress() {
        return new InetSocketAddress("127.0.0.1", 8000);
    }
}
