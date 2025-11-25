package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper  {

    RestaurantEntity toEntity (Restaurant restaurant);

    Restaurant toDomain(RestaurantEntity restaurantEntity);

    List<Restaurant> toDomainList(List<RestaurantEntity> entities);
}
