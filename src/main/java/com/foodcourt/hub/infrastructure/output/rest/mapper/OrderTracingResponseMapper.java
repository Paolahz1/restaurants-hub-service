package com.foodcourt.hub.infrastructure.output.rest.mapper;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.infrastructure.output.rest.dto.tracing.OrderTracingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderTracingResponseMapper {

    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "employeeId", target = "assignedEmployeeId")
    @Mapping(source = "timestamp", target = "timestamp")
    Order toDomain(OrderTracingResponse orderTracingResponse);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "assignedEmployeeId", target = "employeeId")
    OrderTracingResponse toTracing(Order order);


    List<Order> toDomainList (List<OrderTracingResponse> orderTracingResponses);


}

