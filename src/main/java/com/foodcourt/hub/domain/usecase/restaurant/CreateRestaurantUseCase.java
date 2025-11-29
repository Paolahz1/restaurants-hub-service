package com.foodcourt.hub.domain.usecase.restaurant;

import com.foodcourt.hub.domain.exception.*;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserInfoPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

public class CreateRestaurantUseCase implements ICreateRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserInfoPort userVerificationPort;


    public CreateRestaurantUseCase(IRestaurantPersistencePort persistencePort, IUserInfoPort userVerificationPort) {
        this.restaurantPersistencePort = persistencePort;
        this.userVerificationPort = userVerificationPort;
    }


    @Override
    public Restaurant create(Restaurant restaurant) {

        validateOwner(restaurant.getOwnerId());
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

    private void validateOwner(Long ownerId) {
        //Lo pide al otro MS
        String role = userVerificationPort.getUserRole(ownerId);
        if (!"OWNER".equalsIgnoreCase(role)) {
            throw new UserIsNotOwnerException();
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

}
