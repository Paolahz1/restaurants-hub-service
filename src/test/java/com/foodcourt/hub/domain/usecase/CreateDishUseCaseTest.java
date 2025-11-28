package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.usecase.dish.CreateDishUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDishUseCaseTest {

    @Mock
    IDishPersistencePort dishPersistencePort;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    CreateDishUseCase useCase;


    @Test
    void shouldSaveDishWhenOwnerIsCorrect() {
        // Arrange
        Dish dish = new Dish();
        dish.setRestaurantId(1L);
        Long ownerId = 10L;

        when(restaurantPersistencePort.getOwnerIdByRestaurant(1L)).thenReturn(ownerId);

        // Act
        useCase.create(dish, ownerId);

        // Assert
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void shouldThrowInvalidPermissionExceptionWhenOwnerIsWrong() {
        // Arrange
        Dish dish = new Dish();
        dish.setRestaurantId(1L);
        Long ownerId = 10L;

        when(restaurantPersistencePort.getOwnerIdByRestaurant(1L)).thenReturn(99L);

        // Act & Assert
        assertThrows(InvalidPermissionException.class, () -> useCase.create(dish, ownerId));
    }
}