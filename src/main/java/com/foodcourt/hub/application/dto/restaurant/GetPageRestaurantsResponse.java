package com.foodcourt.hub.application.dto.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class GetPageRestaurantsResponse {

    private List<RestaurantSummaryResponse> content;

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;
}
