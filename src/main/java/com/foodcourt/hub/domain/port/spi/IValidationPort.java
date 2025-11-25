package com.foodcourt.hub.domain.port.spi;

public interface IValidationPort {

    boolean validateOwnerOfRestaurant(Long ownerId, Long restaurantId);
    boolean validateOwnerOfDish(Long ownerId, Long dishId);
}
