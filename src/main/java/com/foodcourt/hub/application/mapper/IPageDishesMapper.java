package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.DishSummaryResponse;
import com.foodcourt.hub.application.dto.GetPageDishesResponse;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IPageDishesMapper {


    List<DishSummaryResponse> toSummaryList(List<Dish> restaurants);

    default Category map(String category) {
        return category != null ? Category.valueOf(category.toUpperCase()) : null;
    }

    default GetPageDishesResponse toResponse (Page<Dish> pageDish){

         return  GetPageDishesResponse.builder()
                .content(toSummaryList(pageDish.getContent()))
                .page(pageDish.getNumber())
                .size(pageDish.getSize())
                .totalElements(pageDish.getTotalElements())
                .isFirst(pageDish.isFirst())
                .isLast(pageDish.isLast())
                .build();

    }


}
