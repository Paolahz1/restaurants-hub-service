package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderItemEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true) // 👈 importante para evitar recursión y conflictos
    OrderItemEntity toEntity(OrderItem orderItem);

    OrderItem toDomain(OrderItemEntity orderItemEntity);
}
