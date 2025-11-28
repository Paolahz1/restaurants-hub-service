package com.foodcourt.hub.domain.port.spi;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.PageModel;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);
    Dish findByID(Long id);
    PageModel<Dish> getPageFromDb(int page, int size, long restaurantId, Category category);
}
