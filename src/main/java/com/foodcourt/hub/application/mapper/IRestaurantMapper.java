package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel  = "spring")
public interface IRestaurantMapper {

    @Mapping(source = "restaurantName", target = "name" )
    @Mapping(source = "phone", target = "phoneNumber")
    Restaurant toDomain(CreateRestaurantCommand command);


    @Mapping(source = "id", target = "restaurantId" )
    @Mapping(target = "success", constant = "true")
    @Mapping(source = "name", target = "restaurantName" )
    CreateRestaurantResponse toResponse(Restaurant restaurant);
}
