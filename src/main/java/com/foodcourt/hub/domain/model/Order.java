package com.foodcourt.hub.domain.model;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private long id;
    private long restaurantId;
    private long clientId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Long assignedEmployeeId;
    private String securityPin;
}
