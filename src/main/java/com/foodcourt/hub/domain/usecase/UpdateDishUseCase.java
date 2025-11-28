package com.foodcourt.hub.domain.usecase;

import com.foodcourt.hub.domain.exception.DishNotFoundException;
import com.foodcourt.hub.domain.exception.InvalidPermissionException;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.port.api.dish.IUpdateDishServicePort;
import com.foodcourt.hub.domain.port.spi.IDishPersistencePort;
import com.foodcourt.hub.domain.port.spi.IValidationUsersPort;


public class UpdateDishUseCase implements IUpdateDishServicePort {

    private  final IDishPersistencePort persistencePort;
    private  final IValidationUsersPort validationPort;

    public UpdateDishUseCase(IDishPersistencePort persistencePort, IValidationUsersPort validationPort) {
        this.persistencePort = persistencePort;
        this.validationPort = validationPort;
    }

    @Override
    public void updateDish(Long idDish, Long price, String description, Long ownerId) {

       if (!validationPort.validateOwnerOfDish(ownerId, idDish)){
            throw new InvalidPermissionException();
       }

       Dish dish = persistencePort.findByID(idDish);
       if(dish == null){
            throw new DishNotFoundException();
       }

        dish.setDescription(description);
        dish.setPrice(price);

        persistencePort.saveDish(dish);

    }
}
