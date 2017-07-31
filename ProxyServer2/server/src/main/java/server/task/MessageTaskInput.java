package server.task;

import common.message.Message;
import common.task.TaskInput;
import common.util.StringUtil;

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
