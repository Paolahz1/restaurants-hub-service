package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPageDishesUseCaseTest {

    @Mock
    IDishPersistencePort persistencePort;

    @InjectMocks
    GetPageDishesUseCase useCase;

    @Test
    void shouldReturnPageOfDishes() {
        int page = 0;
        int size = 1;
        long restaurantId = 1L;

        Category category = Category.ENTRADA;

        Dish d1 = Dish.builder()
                .name("Papitas")
                .price(2000L)
                .restaurantId(restaurantId)
                .description("Gut")
                .category(category)
                .urlImage("image")
                .build();
        List<Dish> dishList = List.of(d1);
        Page<Dish> mockPage = new PageImpl<>(dishList, PageRequest.of(page, size), 10);
        when(persistencePort.getPageFromDb(page, size, restaurantId, category)).thenReturn(mockPage);

        Page<Dish> result = useCase.getPage(page, size, restaurantId, category);

        assertEquals(1, result.getContent().size());
        assertEquals("Papitas", result.getContent().get(0).getName());
        assertEquals("image", result.getContent().get(0).getUrlImage());
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getTotalPages());

    }

}