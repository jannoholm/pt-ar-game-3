package com.playtech.ptargame3.server.task.game;

import com.playtech.ptargame3.api.lobby.PendingGamePlayer;
import com.playtech.ptargame3.api.lobby.PushGameLobbyUpdateMessage;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.exception.SystemException;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.registry.GameRegistryGamePlayer;
import com.playtech.ptargame3.server.task.AbstractLogic;
import com.playtech.ptargame3.server.task.GameUpdateTaskInput;
import com.playtech.ptargame3.server.util.TeamConverter;


public class PushGameLobbyUpdateLogic extends AbstractLogic {
    public static final String TASK_TYPE = "lobby-push";

    public PushGameLobbyUpdateLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        GameUpdateTaskInput input = getTaskInput(task);
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(input.getGameId());

        for (String clientId : game.getSubscribers()) {
            PushGameLobbyUpdateMessage message = getLogicResources().getMessageParser().createMessage(PushGameLobbyUpdateMessage.class);
            message.getHeader().setClientId(clientId);
            message.setAiType(game.getAiType());
            message.setGameId(game.getGameId());
            message.setGameName(game.getGameName());
            message.setFreePlaces(game.getPositions()-game.getPlayers().size());
            message.setTotalPlaces(game.getPositions());
            for (GameRegistryGamePlayer player : game.getPlayers()) {
                PendingGamePlayer rider = new PendingGamePlayer();
                rider.setClientId(player.getClientId());
                rider.setName(getLogicResources().getClientRegistry().getName(player.getClientId()));
                rider.setPositionInTeam(player.getPositionInTeam());
                rider.setTeam(TeamConverter.convert(player.getTeam()));
                message.addPlayer(rider);
            }
            getLogicResources().getCallbackHandler().sendMessage( message );
        }
    }

    private GameUpdateTaskInput getTaskInput(Task task) {
        if (task.getContext().getInput() instanceof GameUpdateTaskInput) {
            return (GameUpdateTaskInput)task.getContext().getInput();
        } else {
            throw new SystemException("Invalid task input for " + PushGameLobbyUpdateLogic.class.getSimpleName());
        }
    }
}
