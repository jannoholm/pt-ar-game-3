package com.playtech.ptargame3.server.task.lobby;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.api.general.JoinServerRequest;
import com.playtech.ptargame3.api.general.JoinServerResponse;
import com.playtech.ptargame3.common.exception.ApiException;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.util.StringUtil;
import com.playtech.ptargame3.server.ContextConstants;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.server.session.ClientSession;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.util.ClientTypeConverter;

import java.util.Collection;


public class JoinServerLogic extends AbstractLogic {

    public JoinServerLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        JoinServerRequest request = getInputRequest(task, JoinServerRequest.class);
        ClientSession session = task.getContext().get(ContextConstants.CONNECTION, ClientSession.class);

        // validate
        if (StringUtil.isNull(request.getHeader().getClientId())) {
            throw new SystemException("ClientId must be generated in session.");
        }
        if (StringUtil.isNull(request.getName())) {
            throw new ApiException(ApiConstants.ERR_NAME_MANDATORY, "Name is mandatory");
        }
        if (StringUtil.isNull(request.getEmail())) {
            throw new ApiException(ApiConstants.ERR_EMAIL_MANDATORY, "Email is mandatory");
        }
        if (session == null) {
            throw new SystemException("Session not found in context.");
        }

        // Determine user
        Collection<User> users = getLogicResources().getDatabaseAccess().getUserDatabase().getUsersByName(request.getName());
        User user;
        if (users.size() == 0) {
            // register user
            User.UserType userType = request.getClientType() == JoinServerRequest.ClientType.BOT ? User.UserType.BOT : User.UserType.REGULAR;
            user = getLogicResources().getDatabaseAccess().getUserDatabase().addUser(request.getName(), request.getEmail(), userType);
        } else {
            // user first user
            user = users.iterator().next();
        }

        // register session
        session.setAuthenticated(user.getId());
        getLogicResources().getClientRegistry().addClientConnection(
                session,
                request.getName(),
                request.getEmail(),
                ClientTypeConverter.convert(request.getClientType())
        );
        session.publishSession();
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        JoinServerResponse response = getResponse(task, JoinServerResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        JoinServerResponse response = getResponse(task, JoinServerResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

}
