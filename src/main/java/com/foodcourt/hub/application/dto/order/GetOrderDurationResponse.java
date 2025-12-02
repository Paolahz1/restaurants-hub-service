package com.foodcourt.hub.application.dto.order;

import com.foodcourt.hub.domain.model.OrderDuration;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetOrderDurationResponse {
    private long restaurantId;
    private List<OrderDuration> orders;
}
