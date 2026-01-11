package com.pulse.common.exception;

import com.pulse.common.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalDbException.class)
    public ResponseEntity<ErrorResponseDTO> handle(GlobalDbException globalDbException)
    {
       if(globalDbException instanceof ConflictException)
       {
           ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ZonedDateTime.now(ZoneOffset.UTC),"FAILED", globalDbException.errorCode, globalDbException.getErrorMessage(), "Error");

           return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
       }
       return
    }
}
