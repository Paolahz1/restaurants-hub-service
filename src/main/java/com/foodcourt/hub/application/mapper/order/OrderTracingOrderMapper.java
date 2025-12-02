package com.foodcourt.hub.application.mapper.order;

import com.foodcourt.hub.application.dto.order.TracingOrderResponse;
import com.foodcourt.hub.domain.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderTracingOrderMapper {

    TracingOrderResponse toTracingOrderResponse(Order order);

    List<TracingOrderResponse> toTracingOrderResponseList(List<Order> orders);

}

