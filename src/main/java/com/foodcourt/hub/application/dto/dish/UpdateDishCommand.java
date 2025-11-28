package com.foodcourt.hub.application.dto.dish;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDishCommand {
    private Long dishId;
    private Long price;
    private String description;

}

