package com.foodcourt.hub.application.dto;

import lombok.Data;

@Data
public class UpdateDishCommand {
    private Long dishId;
    private Long price;
    private String description;

}

