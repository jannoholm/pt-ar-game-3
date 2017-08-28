package com.playtech.ptargame3.test.step.substeps;


import com.playtech.ptargame3.api.camera.LocationNotificationMessage;
import com.playtech.ptargame3.common.task.LogicResources;
import com.playtech.ptargame3.common.task.Task;
import com.playtech.ptargame3.test.step.common.AbstractStep;

import java.nio.ByteBuffer;

public class LocationPushStep extends AbstractStep {

    private ThreadLocal<ByteBuffer> locationDataBuffer = ThreadLocal.withInitial(() -> ByteBuffer.allocateDirect(512));

    public LocationPushStep(LogicResources logicResources) {
        super(logicResources);
    }

    @Override
    public void execute(Task task) {
        LocationNotificationMessage message = createMessage(task, LocationNotificationMessage.class);
        ByteBuffer locationData = locationDataBuffer.get();
        locationData.clear();

        // ball
        locationData.put((byte)100); // id
        locationData.put((byte)720); // x
        locationData.put((byte)410); // y
        locationData.put((byte)0); // rotation

        // car1
        locationData.put((byte)100); // id
        locationData.put((byte)720); // x
        locationData.put((byte)410); // y
        locationData.put((byte)0); // rotation

        // car2
        locationData.put((byte)100); // id
        locationData.put((byte)720); // x
        locationData.put((byte)410); // y
        locationData.put((byte)0); // rotation

        // car3
        locationData.put((byte)100); // id
        locationData.put((byte)720); // x
        locationData.put((byte)410); // y
        locationData.put((byte)0); // rotation

        // car4
        locationData.put((byte)100); // id
        locationData.put((byte)720); // x
        locationData.put((byte)410); // y
        locationData.put((byte)0); // rotation

        locationData.flip();
        byte[] locationBytes = new byte[locationData.remaining()];
        locationData.get(locationBytes);
        message.setLocationData(locationBytes);

        getLogicResources().getCallbackHandler().sendMessage(message);
    }
}
