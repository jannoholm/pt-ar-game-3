package com.playtech.ptargame3.server;


import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.task.Logic;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.MessageTaskWrapper;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.common.task.TaskExecutor;
import com.playtech.ptargame3.common.task.TaskFactory;
import com.playtech.ptargame3.common.task.TaskInput;
import com.playtech.ptargame3.api.lobby.GetGamesRequest;
import com.playtech.ptargame3.server.task.lobby.GetGamesLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageTaskFactory implements TaskFactory {

    private static final Logger logger = Logger.getLogger(MessageTaskFactory.class.getName());

    private final TaskExecutor executor;
    private final LogicResources logicResources;
    private Map<String, Logic> messageToLogic = new HashMap<>();

    public MessageTaskFactory(TaskExecutor executor, LogicResources logicResources) {
        this.executor = executor;
        this.logicResources = logicResources;
    }

    public void initialize() {
        addMapping(GetGamesRequest.class, GetGamesLogic.class);
    }

    private void addMapping(Class<? extends Message> messageClass, Class<? extends Logic> taskClass) {
        try {
            Logic logic = taskClass.getConstructor(LogicResources.class).newInstance(logicResources);
            messageToLogic.put(messageClass.getName(), logic);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to initiate task of type: " + taskClass.getName(), e);
        }
    }

    @Override
    public Task getTask(TaskInput input) {
        Logic logic = messageToLogic.get(input.getType());
        if (logic == null) {
            throw new IllegalArgumentException("Unknown task input of type: " + input.getType());
        }
        return new MessageTaskWrapper(this.executor, logic, input);
    }

    @Override
    public Task getTask(Logic logic, TaskInput input) {
        return new MessageTaskWrapper(this.executor, logic, input);
    }
}
