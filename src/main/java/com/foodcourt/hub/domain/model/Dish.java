package com.foodcourt.hub.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private String urlImage;
    private String category;
    private boolean status;

}
