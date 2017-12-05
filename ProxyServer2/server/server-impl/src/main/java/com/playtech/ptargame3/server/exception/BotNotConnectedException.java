package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;

public class BotNotConnectedException extends ApiException {
    public BotNotConnectedException(String message) {
        super(ApiConstants.ERR_BOT_NOT_CONNECTED, message);
    }

    public BotNotConnectedException(String message, Throwable cause) {
        super(ApiConstants.ERR_BOT_NOT_CONNECTED, message, cause);
    }
}
