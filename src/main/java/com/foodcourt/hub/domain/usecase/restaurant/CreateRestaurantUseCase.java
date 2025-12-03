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

    public CreateRestaurantUseCase(IRestaurantPersistencePort persistencePort ) {
        this.restaurantPersistencePort = persistencePort;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {

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

}
