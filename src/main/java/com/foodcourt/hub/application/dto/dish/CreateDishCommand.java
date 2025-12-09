package com.foodcourt.hub.application.dto.dish;

import jakarta.validation.constraints.Min;
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
    @Min(0)
    private Long price;
    private String description;
    private String urlImage;
    private String category;
    private Long restaurantId;
}
