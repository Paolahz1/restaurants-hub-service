package com.foodcourt.hub.domain.port.spi;
import com.foodcourt.hub.domain.model.Dish;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);
    Dish findByID(Long id);
}
