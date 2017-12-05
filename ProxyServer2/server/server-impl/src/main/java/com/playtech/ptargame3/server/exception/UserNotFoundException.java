package com.playtech.ptargame3.server.exception;

import com.playtech.ptargame3.api.ApiConstants;
import com.playtech.ptargame3.common.exception.ApiException;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String message) {
        super(ApiConstants.ERR_USER_NOT_FOUND, message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(ApiConstants.ERR_USER_NOT_FOUND, message, cause);
    }
}
