package com.foodcourt.hub.domain.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDuration {
    private Long orderId;
    private Long employeeId;
    private long durationInSeconds;
}
