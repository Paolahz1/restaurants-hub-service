package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.UpdateDishCommand;
import com.foodcourt.hub.application.dto.UpdateStatusDishCommand;


public interface IDishHandler {

    void createDish(CreateDishCommand command, Long ownerId);
    void updateDish(UpdateDishCommand command, Long ownerId);
    void updateStatusDish(UpdateStatusDishCommand command, Long ownerId);

}
