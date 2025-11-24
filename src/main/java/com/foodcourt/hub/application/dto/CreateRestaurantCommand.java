package com.foodcourt.hub.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantCommand {

    private String restaurantName;
    private String nit;
    private String address;
    private String phone;
    private String urlLogo;
    private Long ownerId;

}
