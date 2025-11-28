package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.usecase.restaurant.GetPageRestaurantsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPageRestaurantsUseCaseTest {

    @Mock
    IRestaurantPersistencePort persistencePort;

    @InjectMocks
    GetPageRestaurantsUseCase useCase;

    @Test
    void shouldReturnPageOfRestaurants() {
        int page = 0;
        int size = 2;
        Restaurant r1 = new Restaurant(1L, "Rest1", "123", "address", "+111", "logo1.png", 1L);
        Restaurant r2 = new Restaurant(2L, "Rest2", "456", "address", "+222", "logo2.png", 2L);
        List<Restaurant> restaurants = List.of(r1, r2);
        Page<Restaurant> mockPage  = new PageImpl<>(restaurants, PageRequest.of(page, size), 10);
        when(persistencePort.getRestaurants(page, size)).thenReturn(mockPage);

        Page<Restaurant> result = useCase.getPage(page, size);

        assertEquals(2, result.getContent().size());
        assertEquals("Rest1", result.getContent().get(0).getName());
        assertEquals(0, result.getNumber()); //Returns the number the current slice
        assertEquals(2, result.getSize());
        assertEquals(5, result.getTotalPages());
    }
}