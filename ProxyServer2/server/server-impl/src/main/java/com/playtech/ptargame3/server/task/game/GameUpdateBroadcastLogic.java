package com.playtech.ptargame3.server.task.game;

import com.playtech.ptargame3.api.game.GameUpdateBroadcardMessage;
import com.playtech.ptargame3.api.game.GameUpdateMessage;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.exception.GameNotFoundException;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.task.AbstractLogic;


public class GameUpdateBroadcastLogic extends AbstractLogic {
    public GameUpdateBroadcastLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        // input
        GameUpdateBroadcardMessage inMessage = getInputMessage(task, GameUpdateBroadcardMessage.class);

        // get and validate game
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(inMessage.getGameId());

        // broadcast game status update
        for (String subscriberClientId : game.getSubscribers()) {
            GameUpdateMessage outMessage = getLogicResources().getMessageParser().createMessage(GameUpdateMessage.class);
            outMessage.getHeader().setClientId(subscriberClientId);
            outMessage.setGameId(game.getGameId());
            outMessage.setContent(inMessage.getBroadcardContent());
            getLogicResources().getCallbackHandler().sendMessage(outMessage);
        }
    }
}
