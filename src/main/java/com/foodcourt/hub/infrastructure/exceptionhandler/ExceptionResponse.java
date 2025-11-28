package com.foodcourt.hub.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NIT_ALREADY_EXISTS("The provided NIT is already registered"),
    USER_IS_NOT_OWNER("The user is not an owner"),
    INVALID_NIT_FORMAT("The NIT format is invalid"),
    INVALID_PHONE_NUMBER("The phone number format is invalid"),
    INVALID_RESTAURANT_NAME("The restaurant name is invalid"),
    DATABASE_ERROR("There was an error accessing the database"),
    INVALID_PERMISSION("You do not have the permission to make this request"),
    INVALID_CATEGORY("Invalid dish category"),
    HAS_PENDING_ORDERS("The client has pending orders"),
    DISHES_NOT_SAME_RESTAURANT("All dishes in the order must be from the same restaurant");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
