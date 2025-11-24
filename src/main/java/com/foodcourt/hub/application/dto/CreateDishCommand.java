package com.foodcourt.hub.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishCommand {

    private String idOwner; //temporal
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private String category;
    private boolean status;
    private Long restaurantId;
}
