package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;


public class GameNotFoundException extends ApiException {
    public GameNotFoundException(String message) {
        super(ApiConstants.ERR_GAME_NOT_FOUND, message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(ApiConstants.ERR_GAME_NOT_FOUND, message, cause);
    }
}
