package com.foodcourt.hub.infrastructure.output.jpa.entity;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.OrderStatus;

import com.foodcourt.hub.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.foodcourt.hub.infrastructure.output.jpa.respository.IOrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JpaTestRunner implements CommandLineRunner {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper entityMapper;

    public JpaTestRunner(IOrderRepository orderRepository, IOrderEntityMapper entityMapper) {
        this.orderRepository = orderRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public void run(String... args) {

//        System.out.println("==== CREANDO ORDEN DE PRUEBA CON MAPSTRUCT ====");
//
//        Order order = Order.builder()
//                .restaurantId(1L)
//                .clientId(10L)
//                .status(OrderStatus.READY)
//                .createdAt(LocalDateTime.now())
//                .items(List.of(
//                        OrderItem.builder().dishId(100L).quantity(2).build(),
//                        OrderItem.builder().dishId(200L).quantity(1).build()
//                ))
//                .build();
//
//        OrderEntity entity = entityMapper.toEntity(order);
//
//        OrderEntity saved = orderRepository.save(entity);
//
//        System.out.println("Orden guardada con id: " + saved.getId());
//        System.out.println("Items guardados: " + saved.getItems().size());
    }
}
