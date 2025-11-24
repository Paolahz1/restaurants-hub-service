package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.infrastructure.output.jpa.entity.DishEntity;
import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {

    DishEntity toEntity (Dish dish);

    Dish toDomain (DishEntity dishEntity);

    List<Dish> toDomainList(List<DishEntity> entities);
}
