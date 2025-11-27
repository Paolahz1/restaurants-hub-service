package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.DishNotFoundException;
import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.IUpdateStateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationPort;


public class UpdateStateDishUseCase implements IUpdateStateDishServicePort {

   private final IDishPersistencePort persistencePort;
   private final IValidationPort validationService;

    public UpdateStateDishUseCase(IDishPersistencePort persistencePort, IValidationPort validationService) {
        this.persistencePort = persistencePort;
        this.validationService = validationService;
    }

    @Override
    public void updateStateDish(Long dishId, boolean state, Long ownerId) {

        if(!validationService.validateOwnerOfDish(ownerId, dishId)){
            throw new InvalidPermissionException();
        }

        Dish dish = persistencePort.findByID(dishId);
        if(dish == null){
            throw new DishNotFoundException();
        }

        dish.setStatus(state);
        persistencePort.saveDish(dish);

    }
}
