package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.mapper.CreateDishCommandMapper;
import com.foodcourt.hub.application.mapper.IRestaurantMapper;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.domain.port.api.dish.ICreateDishServicePort;
import com.foodcourt.hub.domain.port.api.restaurant.ICreateRestaurantServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandler implements IDishHandler{

    private final CreateDishCommandMapper mapper;
    private final ICreateDishServicePort createDishServicePort;

    @Override
    public void createDish(CreateDishCommand command) {
        Dish dish = mapper.toDish(command);
        Long ownerId = mapper.getOwnerId(command);

        createDishServicePort.create(dish, ownerId);
    }

}
