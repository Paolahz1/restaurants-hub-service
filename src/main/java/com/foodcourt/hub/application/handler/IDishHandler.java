package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;

public interface IDishHandler {

    void createDish(CreateDishCommand command);
}
