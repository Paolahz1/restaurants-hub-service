package com.foodcourt.hub.application.dto.dish;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDishResponse {
    private Long dishId;
    private boolean success;
}

