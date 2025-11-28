package com.foodcourt.hub.application.dto.dish;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DishSummaryResponse {
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private String category;
}
