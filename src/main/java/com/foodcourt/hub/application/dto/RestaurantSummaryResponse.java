package com.foodcourt.hub.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantSummaryResponse {

    private String name;
    private String urlLogo;
}
