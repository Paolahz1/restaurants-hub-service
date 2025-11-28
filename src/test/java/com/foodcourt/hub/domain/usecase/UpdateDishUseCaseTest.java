package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.DishNotFoundException;
import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDishUseCaseTest {

    @Mock
    IDishPersistencePort persistencePort;

    @Mock
    IValidationUsersPort validationPort;

    @InjectMocks
    UpdateDishUseCase useCase;

    @Test
    void shouldUpdateDishWhenOwnerIsValidAndDishExists() {
        Long dishId = 1L;
        Long ownerId = 10L;
        Long price = 500L;
        String description = "New description";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setPrice(100L);
        dish.setDescription("Old description");

        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(true);
        when(persistencePort.findByID(dishId)).thenReturn(dish);

        useCase.updateDish(dishId, price, description, ownerId);

        assertEquals(price, dish.getPrice());
        assertEquals(description, dish.getDescription());
        verify(persistencePort).saveDish(dish);
    }

    @Test
    void shouldThrowExceptionWhenIsNotOwnerOfDish() {
        Long dishId = 1L;
        Long ownerId = 10L;
        Long price = 500L;
        String description = "New description";

        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(false);
        assertThrows(InvalidPermissionException.class, () ->
                useCase.updateDish(dishId, price, description, ownerId )
        );

        verify(persistencePort, never()).saveDish(any());
    }

    @Test
    void shouldThrowDishNotFoundExceptionWhenDishDoesNotExist() {
        Long dishId = 1L;
        Long ownerId = 10L;

        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(true);
        when(persistencePort.findByID(dishId)).thenReturn(null);

        assertThrows(DishNotFoundException.class, () ->
                useCase.updateDish(dishId, 500L, "desc", ownerId)
        );

        verify(persistencePort, never()).saveDish(any());
    }






}