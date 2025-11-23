package com.foodcourt.hub.application.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRestaurantResponse {

    private Long restaurantId;
    private String restaurantName;
    private boolean success;

}
