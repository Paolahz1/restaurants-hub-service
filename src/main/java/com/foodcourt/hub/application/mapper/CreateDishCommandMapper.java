package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.domain.model.Dish;
import org.springframework.stereotype.Component;

@Component
public class CreateDishCommandMapper {

    public Dish toDish(CreateDishCommand command) {
        if (command == null) return null;

        return Dish.builder()
                .name(command.getName())
                .price(command.getPrice())
                .description(command.getDescription())
                .urlImage(command.getUrlImage())
                .category(command.getCategory())
                .status(command.isStatus())
                .restaurantId(command.getRestaurantId())
                .build();
    }

    public Long getOwnerId(CreateDishCommand command) {
        try {
            return Long.parseLong(command.getIdOwner());
        } catch (NumberFormatException e) {
            return null; // o lanzar excepción según tu lógica
        }
    }
}
