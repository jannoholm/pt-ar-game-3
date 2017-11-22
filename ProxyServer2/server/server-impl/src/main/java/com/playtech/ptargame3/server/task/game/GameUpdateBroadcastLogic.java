package com.playtech.ptargame3.server.task.game;

import com.playtech.ptargame3.api.game.GameUpdateBroadcastMessage;
import com.playtech.ptargame3.api.game.GameUpdateMessage;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.ai.GameLogRecord;
import com.playtech.ptargame3.server.registry.GameRegistryGame;
import com.playtech.ptargame3.server.task.AbstractLogic;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameUpdateBroadcastLogic extends AbstractLogic {

    private static final Logger logger = Logger.getLogger(GameUpdateBroadcastLogic.class.getName());

    public GameUpdateBroadcastLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        // input
        GameUpdateBroadcastMessage inMessage = getInputMessage(task, GameUpdateBroadcastMessage.class);

        // log the data
        logGameData(inMessage);

        // get and validate game
        GameRegistryGame game = getLogicResources().getGameRegistry().getGame(inMessage.getGameId());

        // broadcast game status update
        for (String subscriberClientId : game.getSubscribers()) {
            if (subscriberClientId.equals(game.getHostClientId())) continue;

            GameUpdateMessage outMessage = getLogicResources().getMessageParser().createMessage(GameUpdateMessage.class);
            outMessage.getHeader().setClientId(subscriberClientId);
            outMessage.setGameId(game.getGameId());
            outMessage.setContent(inMessage.getBroadcardContent());
            getLogicResources().getCallbackHandler().sendMessage(outMessage);
        }
    }

    private void logGameData(GameUpdateBroadcastMessage inMessage) {
        try {
            GameLogRecord logRecord = new GameLogRecord(inMessage.getGameId());
            logRecord.parse(ByteBuffer.wrap(inMessage.getBroadcardContent()).order(ByteOrder.LITTLE_ENDIAN));
            getLogicResources().getGamelog().writeLog(logRecord);
        } catch (Exception e) {
            logger.log(Level.INFO, "Error writing game log.", e);
        }
    }
}
