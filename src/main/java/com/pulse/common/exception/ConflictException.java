package com.pulse.common.exception;

import lombok.Getter;

@Getter
public class ConflictException extends GlobalDbException {

    public ConflictException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);

    }

}
