package com.foodcourt.hub.domain.port.spi;

public interface IValidationUsersPort {

    boolean validateOwnerOfRestaurant(Long ownerId, Long restaurantId);
    boolean validateOwnerOfDish(Long ownerId, Long dishId);
    boolean clientHasPendingOrders(long clientId);
    boolean validateEmployeeBelongsToRestaurant(long restaurantId, long employeeId);
}
