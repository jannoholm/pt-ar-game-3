package server.message;


import common.message.MessageHeader;
import common.util.StringUtil;

import java.nio.ByteBuffer;

public abstract class AbstractResponse extends AbstractMessage {

    private int errorCode = ApiConstants.ERR_OK;
    private String errorMessage;

    public AbstractResponse(MessageHeader header) {
        super(header);
    }

    @Override
    public void parse(ByteBuffer messageData) {
        errorCode = messageData.getInt();
        errorMessage = StringUtil.readUTF8String(messageData);
    }

    @Override
    public void format(ByteBuffer messageData) {
        messageData.putInt(errorCode);
        StringUtil.writeUTF8String(errorMessage, messageData);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", err=").append(errorCode);
        if (!StringUtil.isNull(errorMessage)) {
            s.append(":").append(errorMessage);
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
