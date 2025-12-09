package com.foodcourt.hub.domain.usecase.restaurant;

import com.foodcourt.hub.domain.exception.*;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.model.Role;
import com.foodcourt.hub.domain.model.User;
import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import com.foodcourt.hub.infrastructure.exceptionhandler.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.Map;

public class CreateRestaurantUseCase implements ICreateRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserInfoPort userInfoPort;

    public CreateRestaurantUseCase(IRestaurantPersistencePort persistencePort, IUserInfoPort userInfoPort) {
        this.restaurantPersistencePort = persistencePort;
        this.userInfoPort = userInfoPort;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {

        validateRole(restaurant.getOwnerId());
        validateNitFormat(restaurant.getNit());
        validatePhone(restaurant.getPhoneNumber());
        validateName(restaurant.getName());

        if (restaurantPersistencePort.existsByNit(restaurant.getNit())) {
            throw new NitAlreadyExistsException();
        }
        try {
            return restaurantPersistencePort.saveRestaurant(restaurant);
         } catch (DataIntegrityViolationException | TransactionSystemException e) {
            throw new DatabaseException();
        }

    }

    private void validateNitFormat(String nit) {
        if (!nit.matches("\\d+")) {
            throw new InvalidNitFormatException();
        }
    }

    private void validatePhone(String phone) {
        if (!phone.matches("^\\+?\\d{1,13}$")) {
            throw new InvalidPhoneNumberException();
        }
    }

    private void validateName(String name) {
        if (name.matches("\\d+")) {
            throw new InvalidRestaurantNameException();
        }
    }

    private void validateRole(long ownerId) {
        User owner = userInfoPort.getUserById(ownerId);

        if(!owner.getRole().equals("OWNER")){
            throw new ForbiddenException(
                    ExceptionResponse.USER_IS_NOT_OWNER,
                    Map.of("Provided OwnerId", ownerId)
            );
        }
    }
}
