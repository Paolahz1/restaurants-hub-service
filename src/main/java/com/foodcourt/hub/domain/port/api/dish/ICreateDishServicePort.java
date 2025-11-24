package com.foodcourt.hub.domain.port.api.dish;
import com.foodcourt.hub.domain.model.Dish;

public interface ICreateDishServicePort {

    Dish create(Dish dish);

}
