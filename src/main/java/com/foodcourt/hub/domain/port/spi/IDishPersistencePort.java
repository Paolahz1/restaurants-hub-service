
package com.foodcourt.hub.domain.port.spi;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Restaurant;

public interface IDishPersistencePort {

    Dish saveDish(Dish dish);

    void  updateDish(Long dishId, Long price, String description);
}
