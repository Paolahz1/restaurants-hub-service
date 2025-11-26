package com.foodcourt.hub.domain.port.api.dish;

public interface IUpdateStateDishServicePort {

    void updateStateDish(Long dishId, boolean state, Long ownerId);
}
