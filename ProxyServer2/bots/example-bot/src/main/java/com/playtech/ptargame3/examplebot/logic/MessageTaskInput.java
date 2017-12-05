package com.playtech.ptargame3.examplebot.logic;

import com.playtech.ptargame3.common.message.Message;
import com.playtech.ptargame3.common.task.TaskInput;
import com.playtech.ptargame3.common.util.StringUtil;

public class MessageTaskInput implements TaskInput {
    private final Message message;

    public MessageTaskInput(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public String getId() {
        if (StringUtil.isNull(message.getHeader().getClientId())) {
            return "ID" + message.getHeader().getMessageId();
        } else {
            return "ID" + message.getHeader().getMessageId() + "-" + message.getHeader().getClientId();
        }
    }

    @Override
    public String getType() {
        return message.getClass().getName();
    }
}
