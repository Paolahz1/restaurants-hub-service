package com.foodcourt.hub.domain.port.api.dish;


public interface IUpdateDishServicePort {

    void updateDish(Long idDish, Long Price, String description, Long ownerId);

}
