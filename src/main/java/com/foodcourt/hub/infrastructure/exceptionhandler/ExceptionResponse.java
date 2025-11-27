package com.foodcourt.hub.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NIT_ALREADY_EXISTS("The provided NIT is already registered"),
    USER_IS_NOT_OWNER("The user is not an owner"),
    INVALID_NIT_FORMAT("The NIT format is invalid"),
    INVALID_PHONE_NUMBER("The phone number format is invalid"),
    INVALID_RESTAURANT_NAME("The restaurant name is invalid"),
    DATABASE_ERROR("There was an error accessing the database"),
    INVALID_PERMISSION("You do not have the permission to make this request"),
    INVALID_CATEGOY("Invalid dish category");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
