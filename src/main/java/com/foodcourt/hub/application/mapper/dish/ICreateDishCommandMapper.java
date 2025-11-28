package com.foodcourt.hub.application.mapper.dish;

import com.foodcourt.hub.application.dto.dish.CreateDishCommand;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ICreateDishCommandMapper {

    Dish toDomain(CreateDishCommand command);

    default Category map(String category) {
        return category != null ? Category.valueOf(category.toUpperCase()) : null;
    }
}
