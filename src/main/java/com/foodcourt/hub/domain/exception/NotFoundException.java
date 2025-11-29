package com.foodcourt.hub.domain.exception;

import com.foodcourt.hub.infrastructure.exceptionhandler.ErrorResponse;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;

import java.util.Map;

public class NotFoundException extends RuntimeException{
    private final ErrorResponse error;

    public NotFoundException(ExceptionResponse message, Map<String, Object> additionalInfo) {
        this.error =  new ErrorResponse(404, message, additionalInfo);
    }

    public ErrorResponse getError() { return error; }
}

