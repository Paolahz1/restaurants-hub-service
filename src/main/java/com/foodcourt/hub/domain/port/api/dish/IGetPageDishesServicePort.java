package com.foodcourt.hub.domain.port.api.dish;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.PageModel;

public interface IGetPageDishesServicePort {

    PageModel<Dish> getPage(int page, int size, long restaurantId, Category category);

}
