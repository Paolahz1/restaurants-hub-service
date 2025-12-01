package com.foodcourt.hub.infrastructure.output.rest.mapper;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.infrastructure.output.rest.dto.tracing.OrderTracingCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderTracingCommandMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "assignedEmployeeId", target = "employeeId")
    OrderTracingCommand toTracingCommand(Order order);

}

