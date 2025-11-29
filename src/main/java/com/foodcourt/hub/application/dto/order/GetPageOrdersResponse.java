package com.foodcourt.hub.application.dto.order;

import com.foodcourt.hub.domain.model.Order;
import com.foodcourt.hub.domain.model.OrderItem;
import com.foodcourt.hub.domain.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetPageOrdersResponse {

    private List<OrderResponse> content;
    private long restaurantId;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;


}


