package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IOrderItemEntityMapper.class)
public interface IOrderEntityMapper {

    @Mapping(target = "id", ignore = true) //
    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);
}
