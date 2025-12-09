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
    INVALID_STATUS("The order status is invalid for this operation"),
    HAS_PENDING_ORDERS("The client has pending orders"),
    DISHES_NOT_SAME_RESTAURANT("All dishes in the order must belong to the same restaurant"),
    ORDER_NOT_FOUND("No order found with the given criteria"),
    INVALID_PIN("The pin entered is incorrect"),
    COMPLETED_TRACING_NOT_FOUND("No completed order tracing records found"),
    TRACING_NOT_FOUND("No order tracing records found");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
