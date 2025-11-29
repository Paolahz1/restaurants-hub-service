package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.domain.model.Restaurant;
import com.foodcourt.hub.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper  {

    RestaurantEntity toEntity (Restaurant restaurant);

    Restaurant toDomain(RestaurantEntity restaurantEntity);

    List<Restaurant> toDomainList(List<RestaurantEntity> entities);

    default PageModel<Restaurant> toPageModel(Page<RestaurantEntity> restaurantEntity) {
        List<RestaurantEntity> entityList = restaurantEntity.getContent();
        List<Restaurant> restaurants = toDomainList(entityList);

       return PageModel.<Restaurant>builder()
                .content(restaurants)
                .page(restaurantEntity.getNumber())
                .size(restaurantEntity.getSize())
                .totalPages(restaurantEntity.getTotalPages())
                .totalElements(restaurantEntity.getTotalElements())
                .first(restaurantEntity.isFirst())
                .last(restaurantEntity.isLast()).
                build();
    }
}
