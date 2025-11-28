package com.foodcourt.hub.application.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishCommand {

    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private String category;
    private Long restaurantId;
}
