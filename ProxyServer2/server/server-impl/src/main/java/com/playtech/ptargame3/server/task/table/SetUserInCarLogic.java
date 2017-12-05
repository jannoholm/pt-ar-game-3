package com.playtech.ptargame3.server.task.table;

import com.playtech.ptargame3.api.table.SetUserInCarRequest;
import com.playtech.ptargame3.api.table.SetUserInCarResponse;
import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.exception.BotNotConnectedException;
import com.playtech.ptargame3.server.exception.UserNotFoundException;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.session.ClientSession;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.util.TeamConverter;

import java.util.Collection;
import java.util.logging.Logger;

public class SetUserInCarLogic extends AbstractLogic {

    private static final Logger logger = Logger.getLogger(SetUserInCarLogic.class.getName());

    public SetUserInCarLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        SetUserInCarRequest request = getInputRequest(task, SetUserInCarRequest.class);
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(request.getGameId());
        User user = getLogicResources().getDatabaseAccess().getUserDatabase().getUser(request.getUserId());
        if (user == null) throw new UserNotFoundException("User not found with id: " + request.getUserId());

        Collection<Session> sessions = getLogicResources().getClientRegistry().getClientSession(request.getUserId());
        if (sessions.size() > 0) {
            ClientSession session = (ClientSession)sessions.iterator().next();
            game.addPlayer(session.getClientId(), TeamConverter.convert(request.getTeam()), request.getPositionInTeam());
        } else if (user.getUserType() == User.UserType.BOT) {
            throw new BotNotConnectedException("Bot not connected. Choose another bot.");
        } else {
            logger.info("User not connected. Playing on the table: " + user);
        }

        // TODO notify bot
    }

    @Override
    public void finishSuccess(Task task) {
        super.finishSuccess(task);
        SetUserInCarResponse response = getResponse(task, SetUserInCarResponse.class);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }

    @Override
    public void finishError(Task task, Exception e) {
        super.finishError(task, e);
        SetUserInCarResponse response = getResponse(task, SetUserInCarResponse.class, e);
        getLogicResources().getCallbackHandler().sendMessage(response);
    }
}
