package com.foodcourt.hub.application.mapper.order;

import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICreateOrderMapper {

    Order toDomain (CreateOrderCommand command);

    CreateOrderResponse toResponse(Order order);

}
