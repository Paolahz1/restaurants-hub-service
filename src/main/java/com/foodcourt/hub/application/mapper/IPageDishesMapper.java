package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.DishSummaryResponse;
import com.foodcourt.hub.application.dto.GetPageDishesResponse;
import com.foodcourt.hub.domain.exception.InvalidDishCategoryException;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IPageDishesMapper {


    List<DishSummaryResponse> toSummaryList(List<Dish> restaurants);

    default Category map(String category) {
        if (category == null || category.trim().isEmpty()) {
            return null;
        }
        try {
            return Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw  new InvalidDishCategoryException();
        }
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
