package com.foodcourt.hub.application.dto.order;

import com.foodcourt.hub.domain.model.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {

    @NotNull private long restaurantId;
    private  List<OrderItem> items;

}
