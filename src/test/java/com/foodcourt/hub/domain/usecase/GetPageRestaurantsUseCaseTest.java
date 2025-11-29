package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.spi.IRestaurantPersistencePort;
import com.foodcourt.hub.domain.usecase.restaurant.GetPageRestaurantsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        PageModel<Restaurant> mockPage  = PageModel.<Restaurant>builder()
                .content(restaurants).page(0).size(2).totalElements(10)
                .totalPages(5).first(true).last(false)
                .build();

        when(persistencePort.getRestaurants(page, size)).thenReturn(mockPage);


        PageModel<Restaurant> result = useCase.getPage(page, size);


        assertEquals(2, result.getContent().size());
        assertEquals("Rest1", result.getContent().get(0).getName());
        assertEquals(0, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(5, result.getTotalPages());
    }
}