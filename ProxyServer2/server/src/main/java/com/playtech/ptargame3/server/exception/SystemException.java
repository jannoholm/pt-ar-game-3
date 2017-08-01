package com.playtech.ptargame3.server.exception;


import com.playtech.ptargame3.common.exception.ApiException;
import com.playtech.ptargame3.server.message.ApiConstants;

public class SystemException extends ApiException {
    public SystemException(String message) {
        super(ApiConstants.ERR_SYSTEM, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ApiConstants.ERR_SYSTEM, message, cause);
    }
}
