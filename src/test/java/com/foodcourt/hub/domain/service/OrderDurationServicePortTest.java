package com.foodcourt.hub.domain.service;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OrderDurationServicePortTest {

    private final IOrderDurationServicePort servicePort = new OrderDurationServicePort();

    @Test
    void shouldCalculateOrdersDuration() {

        Instant now = Instant.now();

        Order orderPending = Order.builder()
                .id(1L)
                .timestamp(now)
                .status(OrderStatus.PENDING)
                .build();

        Order orderDelivered = Order.builder()
                .id(1L)
                .timestamp(now.plusSeconds(300))
                .status(OrderStatus.DELIVERED)
                .assignedEmployeeId(10L)
                .build();

        List<OrderDuration> result = servicePort.calculateOrdersDuration(
                List.of(orderPending, orderDelivered)
        );

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(10L, result.get(0).getEmployeeId());
        assertEquals(300L, result.get(0).getDurationInSeconds());
    }

}
