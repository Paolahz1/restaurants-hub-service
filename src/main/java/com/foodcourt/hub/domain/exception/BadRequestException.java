package com.foodcourt.hub.domain.exception;

import com.foodcourt.hub.infrastructure.exceptionhandler.ErrorResponse;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class BadRequestException extends RuntimeException{
    private final ErrorResponse error;

    public BadRequestException(ExceptionResponse message, Map<String, Object> additionalInfo) {
        this.error =  new ErrorResponse(400, message, additionalInfo);
    }

    public ErrorResponse getError() { return error; }
}

