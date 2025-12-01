package com.foodcourt.hub.application.dto.order;

import com.foodcourt.hub.domain.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TracingOrderResponse {

    private OrderStatus status;
    private Instant timestamp;

}
