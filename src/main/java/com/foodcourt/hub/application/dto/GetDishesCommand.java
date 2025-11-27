package com.foodcourt.hub.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class GetDishesCommand {
    private Long restaurantId;
    private int page;
    private int size;
    private String category;
}
