package com.foodcourt.hub.infrastructure.exceptionhandler;

import com.foodcourt.hub.domain.exception.*;
import com.foodcourt.hub.domain.exception.InvalidPhoneNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NitAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleNitAlreadyExistsException(NitAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NIT_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(UserIsNotOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserIsNotOwnerException(UserIsNotOwnerException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_IS_NOT_OWNER.getMessage()));
    }

    @ExceptionHandler(InvalidNitFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNitFormatException(InvalidNitFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_NIT_FORMAT.getMessage()));
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantInvalidPhoneNumberException(InvalidPhoneNumberException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_PHONE_NUMBER.getMessage()));
    }

    @ExceptionHandler(InvalidRestaurantNameException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRestaurantNameException(InvalidRestaurantNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_RESTAURANT_NAME.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseException(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DATABASE_ERROR.getMessage()));
    }

    @ExceptionHandler(InvalidPermissionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPermissionException(InvalidPermissionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_PERMISSION.getMessage()));
    }

    @ExceptionHandler(InvalidDishCategoryException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCategory(InvalidDishCategoryException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INVALID_CATEGORY.getMessage()));
    }

    @ExceptionHandler(HasPendingOrdersException.class)
    public ResponseEntity<Map<String, String>> handleClientHasPendingOrders(HasPendingOrdersException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.HAS_PENDING_ORDERS.getMessage()));
    }

    @ExceptionHandler(DishesNotFromSameRestaurant.class)
    public ResponseEntity<Map<String, String>> handleDishesNotFromSameRestaurant(DishesNotFromSameRestaurant ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DISHES_NOT_SAME_RESTAURANT.getMessage()));
    }

}
