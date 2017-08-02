package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;


public class CannotJoinException extends ApiException {
    public CannotJoinException(String message) {
        super(ApiConstants.ERR_GAME_JOIN, message);
    }

    public CannotJoinException(String message, Throwable cause) {
        super(ApiConstants.ERR_GAME_JOIN, message, cause);
    }
}
