package com.foodcourt.hub.application.mapper;

import com.foodcourt.hub.application.dto.CreateOrderCommand;
import com.foodcourt.hub.application.dto.CreateOrderResponse;
import com.foodcourt.hub.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICreateOrderMapper {

    Order toDomain (CreateOrderCommand command);

    CreateOrderResponse toResponse(Order order);

}
