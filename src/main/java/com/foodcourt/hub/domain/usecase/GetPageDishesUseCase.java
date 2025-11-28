package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.IGetPageDishesServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import org.springframework.data.domain.Page;

public class GetPageDishesUseCase implements IGetPageDishesServicePort {

    private final IDishPersistencePort persistencePort;

    public GetPageDishesUseCase(IDishPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Page<Dish> getPage(int page, int size, long restaurantId, Category category) {

        return persistencePort.getPageFromDb(page, size, restaurantId, category);
    }

}
