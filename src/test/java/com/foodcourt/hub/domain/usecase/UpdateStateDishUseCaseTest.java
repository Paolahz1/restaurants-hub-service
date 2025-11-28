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
class UpdateStateDishUseCaseTest {

    @Mock
    IDishPersistencePort persistencePort;

    @Mock
    IValidationUsersPort validationPort;

    @InjectMocks
    UpdateStateDishUseCase useCase;

    @Test
    void shouldUpdateDishStateWhenOwnerIsValidAndDishExists() {
        Long dishId = 1L;
        Long ownerId = 10L;
        boolean state = false;

        Dish dish = new Dish();
        dish.setStatus(true);
        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(true);
        when(persistencePort.findByID(dishId)).thenReturn(dish);

        useCase.updateStateDish(dishId, state, ownerId); //Esta es la única parte donde ocurre un cambio real.

        assertEquals(state, dish.isStatus());
        verify(persistencePort).saveDish(dish);
    }

    @Test
    void shouldThrowExceptionWhenIsNotOwnerOfDish() {
        Long dishId = 1L;
        Long ownerId = 10L;
        boolean state = false;

        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(false);

        assertThrows(InvalidPermissionException.class, () ->
                useCase.updateStateDish(dishId, state,ownerId )
        );

        verify(persistencePort, never()).saveDish(any());
    }


    @Test
    void shouldThrowDishNotFoundExceptionWhenDishDoesNotExist() {
        Long dishId = 1L;
        Long ownerId = 10L;
        boolean state = false;

        // Dish dish = new Dish();
        // dish.setStatus(true);
        when(validationPort.validateOwnerOfDish(ownerId, dishId)).thenReturn(true);
         when(persistencePort.findByID(dishId)).thenReturn(null);

        assertThrows(DishNotFoundException.class, () ->
                useCase.updateStateDish(dishId, state, ownerId));

        verify(persistencePort, never()).saveDish(any());
    }



}