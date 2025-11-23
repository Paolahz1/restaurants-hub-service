package com.foodcourt.hub.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantResponse {

    private Long restaurantId;
    private String restaurantName;
    private boolean success;

}
