package com.foodcourt.hub.application.handler;

import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.GetPageRestaurantsResponse;

public interface IRestaurantHandler {

    CreateRestaurantResponse createRestaurant(CreateRestaurantCommand restaurantCommand);
    GetPageRestaurantsResponse getPageRestaurants(int page, int size);
}
