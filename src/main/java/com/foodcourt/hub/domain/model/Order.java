package com.foodcourt.hub.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long id;
    private long restaurantId;
    private long clientId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;

}
