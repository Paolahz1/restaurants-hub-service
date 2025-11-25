package com.foodcourt.hub.domain.port.api.dish;
import com.foodcourt.hub.domain.model.Dish;

public interface IUpdateDishServicePort {

    void updateDish(Long idDish, Long Price, String description, Long ownerId);

}
