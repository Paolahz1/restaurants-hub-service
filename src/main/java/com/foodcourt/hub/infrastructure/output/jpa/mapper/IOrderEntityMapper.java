package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = IOrderItemEntityMapper.class)
public interface IOrderEntityMapper {

    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);

    List<Order> toDomainList(List<OrderEntity> entities);

   default PageModel<Order> toPageModel (Page<OrderEntity> entityPage){

       List<OrderEntity> entities = entityPage.getContent();
       List<Order> orders = toDomainList(entities);

       return  PageModel.<Order>builder()
               .content(orders)
               .page(entityPage.getNumber())
               .size(entities.size())
               .totalPages(entityPage.getTotalPages())
               .totalElements(entityPage.getTotalElements())
               .first(entityPage.isFirst())
               .last(entityPage.isLast())
               .build();
    }
}


