package com.foodcourt.hub.application.mapper.dish;

import com.foodcourt.hub.application.dto.dish.DishSummaryResponse;
import com.foodcourt.hub.application.dto.dish.GetPageDishesResponse;
import com.foodcourt.hub.domain.exception.InvalidDishCategoryException;
import com.foodcourt.hub.domain.model.Category;
import com.foodcourt.hub.domain.model.Dish;
import com.foodcourt.hub.domain.model.PageModel;
import org.mapstruct.Mapper;

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

    default GetPageDishesResponse toResponse (PageModel<Dish> pageDish){

         return  GetPageDishesResponse.builder()
                .content(toSummaryList(pageDish.getContent()))
                .page(pageDish.getPage())
                .size(pageDish.getSize())
                .totalElements(pageDish.getTotalElements())
                .isFirst(pageDish.isFirst())
                .isLast(pageDish.isLast())
                .build();
    }


}
