package com.foodcourt.hub.infrastructure.output.rest.dto.tracing;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderTracingResponse {

    private Long orderId;
    private Long clientId;
    private Long employeeId;
    private Long restaurantId;
    private String status;
    private Instant timestamp;

}
