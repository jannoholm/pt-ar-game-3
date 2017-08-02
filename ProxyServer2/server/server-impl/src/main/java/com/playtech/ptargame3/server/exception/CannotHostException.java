package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;


public class CannotHostException extends ApiException {
    public CannotHostException(String message) {
        super(ApiConstants.ERR_GAME_HOST, message);
    }

    public CannotHostException(String message, Throwable cause) {
        super(ApiConstants.ERR_GAME_HOST, message, cause);
    }
}
