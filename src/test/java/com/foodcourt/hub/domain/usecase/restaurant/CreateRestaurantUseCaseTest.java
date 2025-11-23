package com.foodcourt.hub.domain.usecase.restaurant;

import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.port.spi.IUserVerificationPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    IRestaurantPersistencePort persistencePort;
    @Mock
    IUserVerificationPort verificationPort;

    @InjectMocks
    CreateRestaurantUseCase useCase;


    @Test
    void shouldCreateRestaurantSuccessfully() {
        Restaurant testRestaurant = Restaurant.builder()
                .name("La Buena Mesa")
                .nit("123456789")
                .address("Calle 123")
                .phoneNumber("+573001112233")
                .urlLogo("http://logo.com/logo.png")
                .ownerId(1L)
                .build();

        when(persistencePort.existsByNit(testRestaurant.getNit())).thenReturn(false);
        when(verificationPort.getUserRole(testRestaurant.getOwnerId())).thenReturn("OWNER");
        when(persistencePort.saveRestaurant(testRestaurant)).thenReturn(testRestaurant);

        Restaurant created = useCase.create(testRestaurant);

        verify(persistencePort).saveRestaurant(argThat(r ->
                r.getName().equals("La Buena Mesa") &&
                        r.getNit().equals("123456789") &&
                        r.getOwnerId().equals(1L)
        ));
    }

}