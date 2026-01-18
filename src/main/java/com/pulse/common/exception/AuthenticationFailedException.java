package com.pulse.common.exception;

public class AuthenticationFailedException extends  GlobalDbException {


    public AuthenticationFailedException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
