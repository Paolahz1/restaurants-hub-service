package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.domain.model.Dish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICreateDishCommandMapper {

    Dish toDomain(CreateDishCommand command);

}
