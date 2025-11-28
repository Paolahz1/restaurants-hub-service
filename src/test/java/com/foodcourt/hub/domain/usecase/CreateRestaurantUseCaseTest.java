package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.DatabaseException;
import com.foodcourt.hub.domain.exception.InvalidPhoneNumberException;
import com.foodcourt.hub.domain.exception.UserIsNotOwnerException;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import com.foodcourt.hub.domain.usecase.restaurant.CreateRestaurantUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    IRestaurantPersistencePort persistencePort;
    @Mock
    IUserVerificationPort verificationPort;

    @InjectMocks
    CreateRestaurantUseCase useCase;

    private Restaurant createValidRestaurant() {
        return Restaurant.builder()
                .name("La Buena Mesa")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001112233")
                .urlLogo("http://logo.com/logo.png")
                .ownerId(1L)
                .build();
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        Restaurant restaurant = createValidRestaurant();

        when(persistencePort.existsByNit(restaurant.getNit())).thenReturn(false);
        when(verificationPort.getUserRole(restaurant.getOwnerId())).thenReturn("OWNER");
        when(persistencePort.saveRestaurant(restaurant)).thenReturn(restaurant);

        useCase.create(restaurant);

        verify(persistencePort).saveRestaurant(restaurant);
    }

    @Test
    void shouldThrowUserIsNotOwnerException() {
        Restaurant restaurant = createValidRestaurant();

        when(verificationPort.getUserRole(restaurant.getOwnerId())).thenReturn("EMPLOYEE");

        assertThrows(UserIsNotOwnerException.class, () -> useCase.create(restaurant));
        verify(persistencePort, never()).saveRestaurant(any());
    }


    @Test
    void shouldThrowInvalidPhoneNumberException() {
        Restaurant restaurant = createValidRestaurant();
        restaurant.setPhoneNumber("123ABC");

        when(verificationPort.getUserRole(restaurant.getOwnerId())).thenReturn("OWNER");

        assertThrows(InvalidPhoneNumberException.class, () -> useCase.create(restaurant));
        verify(persistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowDatabaseExceptionOnDataIntegrityViolation() {
        Restaurant restaurant = createValidRestaurant();

        when(verificationPort.getUserRole(restaurant.getOwnerId())).thenReturn("OWNER");
        when(persistencePort.existsByNit(restaurant.getNit())).thenReturn(false);
        when(persistencePort.saveRestaurant(restaurant)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DatabaseException.class, () -> useCase.create(restaurant));
    }

    @Test
    void shouldThrowDatabaseExceptionOnTransactionSystemException() {
        Restaurant restaurant = createValidRestaurant();

        when(verificationPort.getUserRole(restaurant.getOwnerId())).thenReturn("OWNER");
        when(persistencePort.existsByNit(restaurant.getNit())).thenReturn(false);
        when(persistencePort.saveRestaurant(restaurant)).thenThrow(TransactionSystemException.class);

        assertThrows(DatabaseException.class, () -> useCase.create(restaurant));
    }

}