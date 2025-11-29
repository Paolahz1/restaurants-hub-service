package com.foodcourt.hub.application.mapper.order;

import com.foodcourt.hub.application.dto.order.GetPageOrdersResponse;
import com.foodcourt.hub.application.dto.order.OrderResponse;
import com.foodcourt.hub.domain.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPageOrdersMapper {

    List<OrderResponse> toListOrderResponse(List<Order> orders);

    default GetPageOrdersResponse toResponse (PageModel<Order> pageModel){

        List<OrderResponse> orderResponses = toListOrderResponse(pageModel.getContent());

        long restaurantId = orderResponses.isEmpty() ? 0 : orderResponses.get(0).getRestaurantId();

        return GetPageOrdersResponse.builder()
                .content(orderResponses)
                .restaurantId(restaurantId)
                .page(pageModel.getPage())
                .size((pageModel.getSize()))
                .totalElements(pageModel.getTotalElements())
                .totalPages(pageModel.getTotalPages())
                .first(pageModel.isFirst())
                .last(pageModel.isLast())
                .build();

    }

}
