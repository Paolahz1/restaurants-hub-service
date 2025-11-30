package com.foodcourt.hub.infrastructure.output.jpa.mapper;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.PageModel;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderEntity;
import com.foodcourt.hub.infrastructure.output.jpa.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = IOrderItemEntityMapper.class)
public interface IOrderEntityMapper {

        // MapStruct solo mapea campos simples
        OrderEntity toEntityWithoutItems(Order order);

        Order toDomain(OrderEntity entity);

        default OrderEntity toEntity(Order order) {
            OrderEntity entity = toEntityWithoutItems(order);

            entity.setItems(new ArrayList<>());  // evitar null pointer

            if (order.getItems() != null) {

                for (OrderItem item : order.getItems()) {
                    OrderItemEntity itemEntity = new OrderItemEntity();

                    itemEntity.setDishId(item.getDishId());
                    itemEntity.setQuantity(item.getQuantity());

                    itemEntity.setOrder(entity);

                    // agregar a la lista del padre
                    entity.getItems().add(itemEntity);
                }
            }

            return entity;
        }


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


