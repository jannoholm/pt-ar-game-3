package com.playtech.ptargame3.examplebot.logic;

import com.playtech.ptargame3.api.game.GameUpdateMessage;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicRegistry;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.examplebot.logic.table.GameUpdateLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BotLogicRegistry implements LogicRegistry {
    private static final Logger logger = Logger.getLogger(BotLogicRegistry.class.getName());

    private LogicResources logicResources;

    private Map<String, Logic> messageToLogic = new HashMap<>();

    public void initialize(LogicResources logicResources) {
        this.logicResources = logicResources;
        addMapping(GameUpdateMessage.class.getName(), GameUpdateLogic.class);
    }

    private void addMapping(String taskType, Class<? extends Logic> taskClass) {
        try {
            Logic logic = taskClass.getConstructor(LogicResources.class).newInstance(logicResources);
            messageToLogic.put(taskType, logic);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to initiate task of type: " + taskClass.getName(), e);
        }
    }

    @Override
    public Logic getLogic(String type) {
        return messageToLogic.get(type);
    }
}
