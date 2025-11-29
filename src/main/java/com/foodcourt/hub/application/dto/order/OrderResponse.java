package com.foodcourt.hub.application.dto.order;


import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private long id;
    private long restaurantId;
    private long clientId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;
}


