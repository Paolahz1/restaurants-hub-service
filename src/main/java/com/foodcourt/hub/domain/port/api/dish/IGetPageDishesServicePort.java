package com.foodcourt.hub.domain.port.api.dish;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import org.springframework.data.domain.Page;

public interface IGetPageDishesServicePort {

    Page<Dish> getPage(int page, int size, long restaurantId, Category category);

}
