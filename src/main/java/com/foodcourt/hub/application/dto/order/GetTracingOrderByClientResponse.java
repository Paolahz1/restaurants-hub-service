package com.foodcourt.hub.application.dto.order;

import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
@Builder
public class GetTracingOrderByClientResponse {

    private Long id;
    private long restaurantId;
    List<TracingOrderResponse> tracingOrder;
}
