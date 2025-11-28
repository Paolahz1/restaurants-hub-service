package com.foodcourt.hub.application.dto.restaurant;


import lombok.Builder;
import lombok.*;

@Data
@Builder
public class CreateRestaurantResponse {

    private Long restaurantId;
    private String restaurantName;
    private boolean success;

}
