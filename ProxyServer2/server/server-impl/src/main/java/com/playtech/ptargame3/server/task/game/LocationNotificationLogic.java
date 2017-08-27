package com.playtech.ptargame3.server.task.game;


import com.playtech.ptargame3.api.camera.LocationNotificationMessage;
import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.server.task.AbstractLogic;

public class LocationNotificationLogic extends AbstractLogic {
    public LocationNotificationLogic(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        // message from client
        LocationNotificationMessage inMessage = getInputMessage(task, LocationNotificationMessage.class);

        // get and validate game
        for (Session session : getLogicResources().getClientRegistry().getTableSessions()) {
            session.sendMessage(inMessage);
        }
    }
}
