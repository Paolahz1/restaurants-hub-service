package com.foodcourt.hub.domain.service;
import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderDuration;
import com.foodcourt.hub.domain.model.OrderStatus;
import com.foodcourt.hub.domain.port.spi.IOrderDurationServicePort;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDurationServicePort implements IOrderDurationServicePort {

    @Override
    public List<OrderDuration> calculateOrdersDuration(List<Order> events) {

        List<OrderDuration> result = new ArrayList<>();

        for (Order pending : events) {
            if (!pending.getStatus().equals(OrderStatus.PENDING)) continue;

            Long id = pending.getId();
            Instant start = pending.getTimestamp();
            Instant end = null;
            Long employeeId = null;

            for (Order delivered : events) {
                if (delivered.getId().equals(id)
                        && delivered.getStatus().equals(OrderStatus.DELIVERED)) {
                    end = delivered.getTimestamp();
                    employeeId = delivered.getAssignedEmployeeId();
                    break;
                }
            }

            if (end != null) {
                long duration = Duration.between(start, end).getSeconds();

                result.add(
                        OrderDuration.builder()
                                .orderId(id)
                                .employeeId(employeeId)
                                .durationInSeconds(duration)
                                .build()
                );
            }
        }
        return result;
    }


}
