package com.pulse.common.exception;

public class GlobalDbException extends Exception{
    final String errorCode;
    final String errorMessage;

    public GlobalDbException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
