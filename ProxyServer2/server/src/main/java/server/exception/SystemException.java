package server.exception;


import common.exception.ApiException;
import server.message.ApiConstants;

public class SystemException extends ApiException {
    public SystemException(String message) {
        super(ApiConstants.ERR_SYSTEM, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ApiConstants.ERR_SYSTEM, message, cause);
    }
}
