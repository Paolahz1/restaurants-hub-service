package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.UpdateDishCommand;


public interface IDishHandler {

    void createDish(CreateDishCommand command, Long ownerId);
    void updateDish(UpdateDishCommand command, Long ownerId);


}
