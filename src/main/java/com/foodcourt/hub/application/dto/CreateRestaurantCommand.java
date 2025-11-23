package com.foodcourt.hub.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRestaurantCommand {

    private String restaurantName;
    private String nit;
    private String address;
    private String phone;
    private String urlLogo;
    private Long ownerId;

}
