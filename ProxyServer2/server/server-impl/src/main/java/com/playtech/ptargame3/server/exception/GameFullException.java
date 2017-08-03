package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;


public class GameFullException extends ApiException {
    public GameFullException(String message) {
        super(ApiConstants.ERR_GAME_FULL, message);
    }

    public GameFullException(String message, Throwable cause) {
        super(ApiConstants.ERR_GAME_FULL, message, cause);
    }
}
