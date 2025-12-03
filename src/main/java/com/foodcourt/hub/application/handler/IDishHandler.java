package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.dish.*;


public interface IDishHandler {

    void createDish(CreateDishCommand command, Long ownerId);
    void updateDish(UpdateDishCommand command, Long ownerId);
    void updateStatusDish(UpdateStatusDishCommand command, Long ownerId);
    GetPageDishesResponse getDishes(GetPageDishesCommand command);

}
